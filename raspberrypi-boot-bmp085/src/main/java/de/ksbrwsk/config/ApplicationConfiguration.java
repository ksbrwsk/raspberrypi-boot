package de.ksbrwsk.config;

import de.ksbrwsk.bmp085.Bmp085DataEvent;
import de.ksbrwsk.bmp085.Bmp085DataEventPublisher;
import de.ksbrwsk.bmp085.DeviceInformation;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.event.inbound.ApplicationEventListeningMessageProducer;
import org.springframework.integration.json.ObjectToJsonTransformer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author saborowski
 */
@Configuration
@EnableIntegration
@IntegrationComponentScan
@PropertySource("classpath:/application-${spring.profiles.active}.properties")
public class ApplicationConfiguration implements EnvironmentAware {

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
    public DeviceInformation deviceInformation() {
        String deviceId = this.environment.getProperty("deviceId");
        String deviceLocation = this.environment.getProperty("deviceLocation");
        return new DeviceInformation(deviceId, deviceLocation);
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
    @ServiceActivator(inputChannel = "mqttOut")
    public MqttPahoMessageHandler mqttPahoMessageHandler() {
        String url = this.environment.getProperty("mqtt.url");
        String clientId = this.environment.getProperty("mqtt.clientId");
        String topic = this.environment.getProperty("mqtt.temperature.topic");
        MqttPahoMessageHandler mqttPahoMessageHandler = new MqttPahoMessageHandler(url, clientId, this.defaultMqttPahoClientFactory());
        mqttPahoMessageHandler.setConverter(this.defaultPahoMessageConverter());
        mqttPahoMessageHandler.setDefaultTopic(topic);
        return mqttPahoMessageHandler;
    }

    @Bean
    public PublishSubscribeChannel newBmp085DataEvent() {
        return new PublishSubscribeChannel();
    }

    @Bean
    public DirectChannel mqttOut() {
        return new DirectChannel();
    }

    @Bean
    public DirectChannel bmp085Data() {
        return new DirectChannel();
    }

    @Bean
    @Transformer(inputChannel = "bmp085Data", outputChannel = "mqttOut")
    public ObjectToJsonTransformer objectToJsonTransformer() {
        return new ObjectToJsonTransformer();
    }

//    @Bean
//    public Bmp085DataEventPublisher bmp085DataEventPublisher() {
//        return new Bmp085DataEventPublisher();
//    }

    @Bean
    public ApplicationEventListeningMessageProducer applicationEventListeningMessageProducer() {
        ApplicationEventListeningMessageProducer messageProducer = new ApplicationEventListeningMessageProducer();
        messageProducer.setEventTypes(Bmp085DataEvent.class);
        messageProducer.setOutputChannel(this.newBmp085DataEvent());
        return messageProducer;
    }

    @Bean
    public Executor executor() {
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(2);
//        return executor;
        ExecutorService executorVirtualThread = Executors.newVirtualThreadPerTaskExecutor();
        return executorVirtualThread;
    }
}
