package com.example.demo.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @program: boot-kafka
 * @description:
 * @author: 001977
 * @create: 2018-07-11 11:30
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfigurationWithSTOMP implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/clientConnectThis").setAllowedOrigins("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // P2P should conf a /user  ;   broadcast should conf a /topic
        config.enableSimpleBroker("/topic", "/queue", "/user");
        config.setApplicationDestinationPrefixes("/app");   // Client to Server
        config.setUserDestinationPrefix("/user");           // Server to Client
    }
}
