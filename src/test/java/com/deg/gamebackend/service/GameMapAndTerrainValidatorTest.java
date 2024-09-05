package com.deg.gamebackend.service;

import com.deg.gamebackend.entity.terrain.GameMap;
import com.deg.gamebackend.entity.terrain.Terrain;
import com.deg.gamebackend.entity.terrain.TerrainType;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameMapAndTerrainValidatorTest {
    private static Validator validator;
    String name = "some-name";

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void assertFalse_terrains_are_null() {
        String name = "some-name";
        Terrain[][] terrains = null;
        GameMap gameMap = new GameMap(name, terrains);

        Set<ConstraintViolation<GameMap>> violations = validator.validate(gameMap);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void assertFalse_terrains_less_than_2_row() {
        Terrain[][] terrains = {{new Terrain(TerrainType.LAND), new Terrain(TerrainType.LAND)}};
        GameMap gameMap = new GameMap(name, terrains);

        Set<ConstraintViolation<GameMap>> violations = validator.validate(gameMap);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void assertFalse_terrains_row_are_equal_size() {
        Terrain[][] terrains = {{new Terrain(TerrainType.LAND), new Terrain(TerrainType.LAND)},
                {new Terrain(TerrainType.LAND)}};
        GameMap gameMap = new GameMap(name, terrains);

        Set<ConstraintViolation<GameMap>> violations = validator.validate(gameMap);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void assertFalse_terrains_row_is_null() {
        Terrain[][] terrains = {};
        GameMap gameMap = new GameMap(name, terrains);

        Set<ConstraintViolation<GameMap>> violations = validator.validate(gameMap);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void assertFalse_terrain_type_is_null() {
        Terrain[][] terrains = {{new Terrain(null), new Terrain(null)}, {new Terrain(null), new Terrain(null)}};
        GameMap gameMap = new GameMap(name, terrains);

        Set<ConstraintViolation<GameMap>> violations = validator.validate(gameMap);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void assertFalse_inner_terrains_are_null() {
        Terrain[][] terrains = {{null, null}, {null, null}};
        GameMap gameMap = new GameMap(name, terrains);

        Set<ConstraintViolation<GameMap>> violations = validator.validate(gameMap);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void assertFalse_name_is_null() {
        Terrain[][] terrains = {{new Terrain(TerrainType.LAND), new Terrain(TerrainType.LAND)},
                {new Terrain(TerrainType.LAND), new Terrain(TerrainType.LAND)}};
        GameMap gameMap = new GameMap(null, terrains);

        Set<ConstraintViolation<GameMap>> violations = validator.validate(gameMap);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void assertFalse_name_is_less_than_5() {
        Terrain[][] terrains = {{new Terrain(TerrainType.LAND), new Terrain(TerrainType.LAND)},
                {new Terrain(TerrainType.LAND), new Terrain(TerrainType.LAND)}};
        GameMap gameMap = new GameMap("abcd", terrains);

        Set<ConstraintViolation<GameMap>> violations = validator.validate(gameMap);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void assertFalse_name_is_greater_than_20() {
        Terrain[][] terrains = {{new Terrain(TerrainType.LAND), new Terrain(TerrainType.LAND)},
                {new Terrain(TerrainType.LAND), new Terrain(TerrainType.LAND)}};
        GameMap gameMap = new GameMap("qwertyuiopasdfghjklzxcvbnm", terrains);

        Set<ConstraintViolation<GameMap>> violations = validator.validate(gameMap);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void assertTrue_map_is_2_by_2_and_not_null_terrains() {
        Terrain[][] terrains = {{new Terrain(TerrainType.LAND), new Terrain(TerrainType.LAND)},
                {new Terrain(TerrainType.LAND), new Terrain(TerrainType.LAND)}};
        GameMap gameMap = new GameMap(name, terrains);

        Set<ConstraintViolation<GameMap>> violations = validator.validate(gameMap);
        assertTrue(violations.isEmpty());
    }

}
