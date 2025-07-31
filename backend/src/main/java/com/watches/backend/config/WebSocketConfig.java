package com.watches.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private final CustomHandshakeHandler handshakeHandler;
    public WebSocketConfig(CustomHandshakeHandler handshakeHandler) {
        this.handshakeHandler = handshakeHandler;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Enable simple in-memory broker with prefix /topic for broadcasting messages
        config.enableSimpleBroker("/topic", "/queue");
        // Client messages with /app prefix routed to message handling methods
        config.setApplicationDestinationPrefixes("/app");
        config.setUserDestinationPrefix("/user"); // <-- this enables /user/{userId}/... resolution

    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Client connects to /ws endpoint using SockJS fallback
        registry.addEndpoint("/ws").setHandshakeHandler(handshakeHandler).setAllowedOriginPatterns("*").withSockJS();
    }
}
