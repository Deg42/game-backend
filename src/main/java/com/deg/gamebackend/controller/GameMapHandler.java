package com.deg.gamebackend.controller;

import com.deg.gamebackend.controller.validators.GameMapValidator;
import com.deg.gamebackend.entity.terrain.GameMap;
import com.deg.gamebackend.service.GameMapService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.validation.ValidationException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
@Validated
public class GameMapHandler extends TextWebSocketHandler {
    private final GameMapService gameMapService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public GameMapHandler(GameMapService gameMapService) {
        this.gameMapService = gameMapService;
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        JsonNode jsonNode = objectMapper.readTree(message.getPayload());
        String action = jsonNode.get("action").asText();

        switch (action) {
            case "save":
                handleSaveAction(session, jsonNode);
                break;
            case "get":
                handleFindAction(session, jsonNode);
                break;
            case "getAll":
                handleGetAllAction(session);
                break;
            default:
                session.sendMessage(new TextMessage("Invalid action: " + action));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // Acción después de cerrar la conexión, si es necesario
    }

    private void handleSaveAction(WebSocketSession session, JsonNode jsonNode) throws IllegalArgumentException, IOException {
        try {
            GameMap map = objectMapper.treeToValue(jsonNode.get("data"), GameMap.class);
            GameMapValidator.validateGameMap(map);
            GameMap savedMap = gameMapService.save(map);
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(savedMap)));
        } catch (ValidationException e) {
            session.sendMessage(new TextMessage("Validation error: " + e.getMessage()));
        }
    }

    private void handleFindAction(WebSocketSession session, JsonNode jsonNode) throws IOException {
        String id = jsonNode.get("id").asText();
        Optional<GameMap> oMap = gameMapService.findById(id);
        if (oMap.isPresent()) {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(oMap.get())));
        } else {
            session.sendMessage(new TextMessage("GameMap not found for ID: " + id));
        }
    }

    private void handleGetAllAction(WebSocketSession session) throws IOException {
        List<GameMap> maps = gameMapService.findAll();
        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(maps)));
    }
}
