package de.ksbrwsk.config;

import de.ksbrwsk.pushover.PushoverProperties;
import de.ksbrwsk.pushover.TemperatureMessage;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.json.JsonToObjectTransformer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;

/**
 * @author saborowski
 */
@Configuration
@EnableIntegration
@IntegrationComponentScan(basePackages = {"de.ksbrwsk.pushover"})
@PropertySource("classpath:/application-${spring.profiles.active}.properties")
public class PushoverConfiguration implements EnvironmentAware {

    private Environment environment;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }


    @Bean
    public PushoverProperties pushoverProperties() {
        PushoverProperties pushoverProperties = new PushoverProperties();
        pushoverProperties.setApiToken(this.environment.getProperty("pushover.apiToken"));
        pushoverProperties.setUserId(this.environment.getProperty("pushover.userId"));
        pushoverProperties.setPriority(this.environment.getProperty("pushover.priority"));
        pushoverProperties.setTitle(this.environment.getProperty("pushover.title"));
        pushoverProperties.setUrl(this.environment.getProperty("pushover.url"));
        pushoverProperties.setTitleForURL(this.environment.getProperty("pushover.titleForURL"));
        pushoverProperties.setSound(this.environment.getProperty("pushover.sound"));
        return pushoverProperties;
    }

    @Bean
    public DefaultPahoMessageConverter defaultPahoMessageConverter() {
        return new DefaultPahoMessageConverter();
    }

    @Bean
    public DefaultMqttPahoClientFactory defaultMqttPahoClientFactory() {
        DefaultMqttPahoClientFactory defaultMqttPahoClientFactory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(this.environment.getProperty("mqtt.username"));
        options.setPassword(this.environment.getProperty("mqtt.password").toCharArray());
        defaultMqttPahoClientFactory.setConnectionOptions(options);
        return defaultMqttPahoClientFactory;
    }

    @Bean
    public MqttPahoMessageDrivenChannelAdapter mqttPahoMessageDrivenChannelAdapter() {
        String url = this.environment.getProperty("mqtt.url");
        String clientId = this.environment.getProperty("mqtt.clientId");
        String topic = this.environment.getProperty("mqtt.topic");
        MqttPahoMessageDrivenChannelAdapter mqttPahoMessageDrivenChannelAdapter =
                new MqttPahoMessageDrivenChannelAdapter(url, clientId, this.defaultMqttPahoClientFactory(), topic);
        mqttPahoMessageDrivenChannelAdapter.setOutputChannel(this.temperatureJsonData());
        mqttPahoMessageDrivenChannelAdapter.setConverter(this.defaultPahoMessageConverter());
        return mqttPahoMessageDrivenChannelAdapter;
    }

    @Bean
    public PublishSubscribeChannel temperatureJsonData() {
        return new PublishSubscribeChannel();
    }

    @Bean
    public DirectChannel temperatureMessageData() {
        return new DirectChannel();
    }

    @Bean
    @Transformer(inputChannel = "temperatureJsonData", outputChannel = "temperatureMessageData")
    public JsonToObjectTransformer jsonToObjectTransformer() {
        return new JsonToObjectTransformer(TemperatureMessage.class);
    }

}
