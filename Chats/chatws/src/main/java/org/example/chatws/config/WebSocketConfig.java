package org.example.chatws.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Antes: config.enableSimpleBroker("/topic");  (broker en memoria)
        // Ahora: relay hacia RabbitMQ (plugin STOMP, puerto 61613)
        config.enableStompBrokerRelay("/topic", "/queue")
              .setRelayHost("localhost")
              .setRelayPort(61613)
              .setClientLogin("guest")
              .setClientPasscode("guest")
              .setSystemLogin("guest")
              .setSystemPasscode("guest");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").withSockJS();
    }
}