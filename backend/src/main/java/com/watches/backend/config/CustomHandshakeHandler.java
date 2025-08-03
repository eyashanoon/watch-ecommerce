package com.watches.backend.config;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

@Component
public class CustomHandshakeHandler extends DefaultHandshakeHandler {

    @Override
    protected Principal determineUser(@NonNull ServerHttpRequest request,
                                      @NonNull WebSocketHandler wsHandler,
                                      @NonNull Map<String, Object> attributes) {
        // Extract user ID from query param
        String userId = request.getURI().getQuery();
        if (userId != null && userId.startsWith("userId=")) {
            String id = userId.substring(7); // extract value after "userId="
            return () -> id;
        }

        // Fallback if userId not present
        return () -> UUID.randomUUID().toString();
    }
}
