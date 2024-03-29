package com.group1.billsplitter.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
//                .setHandshakeHandler(new CustomHandshakeHandler())
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic","/queue");
//        registry.setUserDestinationPrefix("/user");
    }

//    public static class StompPrincipal implements Principal {
//        private String name;
//
//        public StompPrincipal(String name) {
//            this.name = name;
//        }
//
//        @Override
//        public String getName() {
//            return name;
//        }
//    }
//
//    public static class CustomHandshakeHandler extends DefaultHandshakeHandler {
//        // Custom class for storing principal
//        @Override
//        public Principal determineUser(
//                ServerHttpRequest request,
//                WebSocketHandler wsHandler,
//                Map<String, Object> attributes
//        ) {
//            // Generate principal with UUID as name
//            return new StompPrincipal(UUID.randomUUID().toString());
//        }
//    }
}
