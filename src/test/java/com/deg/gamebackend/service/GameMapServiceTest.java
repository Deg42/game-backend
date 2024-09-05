package com.deg.gamebackend.service;

import com.deg.gamebackend.entity.terrain.GameMap;
import com.deg.gamebackend.entity.terrain.Terrain;
import com.deg.gamebackend.entity.terrain.TerrainType;
import com.deg.gamebackend.repository.GameMapRepository;
import com.deg.gamebackend.service.gamemap.GameMapService;
import jakarta.validation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GameMapServiceTest {

    @Mock
    private GameMapRepository gameMapRepository;

    private Validator validator;

    @InjectMocks
    private GameMapService gameMapService;

    @Captor
    private ArgumentCaptor<GameMap> gameMapCaptor;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        gameMapService = new GameMapService(gameMapRepository, validator);
    }

    @Test
    void testSaveValidGameMap() {
        String name = "some-name";
        Terrain land = new Terrain(TerrainType.LAND);
        Terrain[][] terrains = new Terrain[][]{
                {land, land},
                {land, land}
        };
        GameMap gameMap = new GameMap(name, terrains);

        when(gameMapRepository.save(any(GameMap.class))).thenReturn(gameMap);

        GameMap savedGameMap = gameMapService.save(gameMap);

        verify(gameMapRepository).save(gameMapCaptor.capture());
        assertEquals(gameMap, gameMapCaptor.getValue());
        assertEquals(gameMap, savedGameMap);
    }

    @Test
    void testSaveInvalidGameMap() {
        GameMap gameMap = new GameMap(null, null);

        Set<ConstraintViolation<GameMap>> violations = validator.validate(gameMap);

        assertThrows(ValidationException.class, () -> {
            gameMapService.save(gameMap);
        });

        assertFalse(violations.isEmpty());
        verify(gameMapRepository, never()).save(any(GameMap.class));
    }

    @Test
    void testFindById() {
        String id = "some-id";
        String name = "some-name";
        Terrain land = new Terrain(TerrainType.LAND);
        Terrain[][] terrains = new Terrain[][]{
                {land, land},
                {land, land}
        };
        GameMap gameMap = new GameMap(name, terrains);
        when(gameMapRepository.findById(id)).thenReturn(Optional.of(gameMap));

        Optional<GameMap> foundGameMap = gameMapService.findById(id);

        assertTrue(foundGameMap.isPresent());
        assertEquals(gameMap, foundGameMap.get());
        verify(gameMapRepository).findById(id);
    }

    @Test
    void testFindAll() {
        String name = "some-name";
        Terrain land = new Terrain(TerrainType.LAND);
        Terrain[][] terrains = new Terrain[][]{
                {land, land},
                {land, land}
        };
        List<GameMap> gameMaps = Collections.singletonList(new GameMap(name, terrains));
        when(gameMapRepository.findAll()).thenReturn(gameMaps);

        List<GameMap> foundGameMaps = gameMapService.findAll();

        assertEquals(gameMaps.size(), foundGameMaps.size());
        verify(gameMapRepository).findAll();
    }

    @Test
    void testDeleteById() {
        String id = "some-id";

        doNothing().when(gameMapRepository).deleteById(id);

        gameMapService.deleteById(id);

        verify(gameMapRepository).deleteById(id);
    }

    @Test
    void testDeleteAll() {
        doNothing().when(gameMapRepository).deleteAll();

        gameMapService.deleteAll();

        verify(gameMapRepository).deleteAll();
    }

}
