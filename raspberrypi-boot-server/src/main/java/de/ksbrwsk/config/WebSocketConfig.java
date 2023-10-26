package de.ksbrwsk.config;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.Objects;

/**
 * @author saborowski
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer, EnvironmentAware {

    private Environment environment;

    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/temperature").withSockJS();
    }

    public void configureMessageBroker(MessageBrokerRegistry registry) {
        String clientLogin = this.environment.getProperty("broker.clientLogin");
        String clientPasscode = this.environment.getProperty("broker.clientPasscode");
        String host = this.environment.getProperty("broker.relayHost");
        int port = this.environment.getProperty("broker.relayPort", Integer.class);

        registry.setApplicationDestinationPrefixes("/app")
                .enableStompBrokerRelay("/queue/", "/topic/")
                .setClientLogin(Objects.requireNonNull(clientLogin))
                .setClientPasscode(Objects.requireNonNull(clientPasscode))
                .setRelayHost(Objects.requireNonNull(host))
                .setRelayPort(port);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
