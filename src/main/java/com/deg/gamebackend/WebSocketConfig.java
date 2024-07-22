package com.deg.gamebackend;

import com.deg.gamebackend.controller.GameMapHandler;
import com.deg.gamebackend.service.GameMapService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final GameMapService gameMapService;

    public WebSocketConfig(GameMapService gameMapService) {
        this.gameMapService = gameMapService;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new GameMapHandler(gameMapService), "/game").setAllowedOrigins("*");
        registry.addHandler(new GameMapHandler(gameMapService), "/games").setAllowedOrigins("*");
    }
}
