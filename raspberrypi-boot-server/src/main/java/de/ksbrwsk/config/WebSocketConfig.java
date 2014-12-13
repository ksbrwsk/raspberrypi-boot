package de.ksbrwsk.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

/**
 * @author saborowski
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(new String[]{"/temperature"}).withSockJS();
        registry.addEndpoint(new String[]{"/systeminfo"}).withSockJS();
        registry.addEndpoint(new String[]{"/hello"}).withSockJS();
    }

    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableStompBrokerRelay("/queue/", "/topic/")
                .setClientLogin("guest")
                .setClientPasscode("guest")
                .setRelayHost("localhost")
                .setRelayPort(61613);
        registry.setApplicationDestinationPrefixes("/app");
    }
}
