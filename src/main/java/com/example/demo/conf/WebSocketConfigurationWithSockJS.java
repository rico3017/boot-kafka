package com.example.demo.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

/**
 * @program: boot-kafka
 * @description: this class is never used
 * @author: 001977
 * @create: 2018-07-11 11:09
 */
@Configuration
@EnableWebSocket
public class WebSocketConfigurationWithSockJS implements WebSocketConfigurer {

    @Autowired
    private WebSocketSupport webSocketSupport;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(webSocketSupport, "/sockjs/myHandler")
        .addInterceptors(new HttpSessionHandshakeInterceptor())
        .setAllowedOrigins("http://localhost:8080")
        .withSockJS();
    }

    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(8192);
        container.setMaxBinaryMessageBufferSize(8192);
        return container;
    }



}
