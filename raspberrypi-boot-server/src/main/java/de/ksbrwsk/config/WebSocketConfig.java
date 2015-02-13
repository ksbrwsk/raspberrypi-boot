package de.ksbrwsk.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

/**
 * @author saborowski
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer implements EnvironmentAware {

    private Environment environment;

    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(new String[]{"/temperature"}).withSockJS();
    }

    public void configureMessageBroker(MessageBrokerRegistry registry) {
        String clientLogin = this.environment.getProperty("broker.clientLogin");
        String clientPasscode = this.environment.getProperty("broker.clientPasscode");
        String host = this.environment.getProperty("broker.relayHost");
        int port = this.environment.getProperty("broker.relayPort", Integer.class);

        registry.enableStompBrokerRelay("/queue/", "/topic/")
                .setClientLogin(clientLogin)
                .setClientPasscode(clientPasscode)
                .setRelayHost(host)
                .setRelayPort(port);
        registry.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
