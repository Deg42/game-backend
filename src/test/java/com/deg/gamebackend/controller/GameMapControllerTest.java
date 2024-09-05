package com.deg.gamebackend.controller;

import com.deg.gamebackend.entity.terrain.GameMap;
import com.deg.gamebackend.entity.terrain.Terrain;
import com.deg.gamebackend.entity.terrain.TerrainType;
import com.deg.gamebackend.service.gamemap.GameMapService;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GameMapController.class)
public class GameMapControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameMapService gameMapService;

    private GameMap gameMap;

    @BeforeEach
    void setUp() {
        Terrain landTerrain = new Terrain(TerrainType.LAND);
        gameMap = new GameMap("some-name", new Terrain[][]{
                {landTerrain, landTerrain},
                {landTerrain, landTerrain}});
        gameMap.setId("some-id");
    }

    @Test
    void testCreateGameMap() throws Exception {
        when(gameMapService.save(any(GameMap.class))).thenReturn(gameMap);

        mockMvc.perform(post("/api/gamemaps")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\": \"some-id\", \"name\": \"Test Map\" }")) // JSON representation of GameMap
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("some-id"));
    }

    @Test
    void testCreateGameMapValidationException() throws Exception {
        when(gameMapService.save(any(GameMap.class))).thenThrow(new ValidationException("Validation failed"));

        mockMvc.perform(post("/api/gamemaps")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\": \"some-id\"}")) // JSON representation of invalid GameMap
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Validation failed"));
    }

    @Test
    void testGetGameMapById() throws Exception {
        when(gameMapService.findById("some-id")).thenReturn(Optional.of(gameMap));

        mockMvc.perform(get("/api/gamemaps/some-id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("some-id"));
    }

    @Test
    void testGetGameMapByIdNotFound() throws Exception {
        when(gameMapService.findById("some-id")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/gamemaps/some-id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetAllGameMaps() throws Exception {
        List<GameMap> gameMaps = Collections.singletonList(gameMap);
        when(gameMapService.findAll()).thenReturn(gameMaps);

        mockMvc.perform(get("/api/gamemaps")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("some-id"));
    }

    @Test
    void testDeleteGameMapById() throws Exception {
        mockMvc.perform(delete("/api/gamemaps/some-id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteAllGameMap() throws Exception {
        mockMvc.perform(delete("/api/gamemaps")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
