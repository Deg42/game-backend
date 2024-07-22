package com.deg.gamebackend.controller.validators;

import com.deg.gamebackend.entity.terrain.GameMap;
import com.deg.gamebackend.entity.terrain.Terrain;
import com.deg.gamebackend.entity.terrain.TerrainType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.*;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameMapValidatorTest {
    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void whenValidTerrainMatrix_thenNoViolations() {
        Terrain[][] terrains = {
                { new Terrain(TerrainType.LAND), new Terrain(TerrainType.SEA) },
                { new Terrain(TerrainType.MOUNTAIN), new Terrain(TerrainType.LAND) }
        };
        GameMap gameMap = new GameMap();
        gameMap.setTerrains(terrains);

        Set<ConstraintViolation<GameMap>> violations = validator.validate(gameMap);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void whenNonRectangularMatrix_thenViolations() {
        Terrain[][] terrains = {
                { new Terrain(TerrainType.LAND), new Terrain(TerrainType.SEA) },
                { new Terrain(TerrainType.MOUNTAIN) }
        };
        GameMap gameMap = new GameMap();
        gameMap.setTerrains(terrains);

        Set<ConstraintViolation<GameMap>> violations = validator.validate(gameMap);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void whenMatrixContainsNullTerrain_thenViolations() {
        Terrain[][] terrains = {
                { new Terrain(TerrainType.LAND), null },
                { new Terrain(TerrainType.MOUNTAIN), new Terrain(TerrainType.LAND) }
        };
        GameMap gameMap = new GameMap();
        gameMap.setTerrains(terrains);

        Set<ConstraintViolation<GameMap>> violations = validator.validate(gameMap);
        assertFalse(violations.isEmpty());
    }

//    @Test
//    public void whenMatrixContainsNullTerrainType_thenViolations() {
//        Terrain[][] terrains = {
//                { new Terrain(TerrainType.LAND), new Terrain() },
//                { new Terrain(TerrainType.MOUNTAIN), new Terrain(TerrainType.LAND) }
//        };
//        GameMap gameMap = new GameMap();
//        gameMap.setTerrains(terrains);
//
//        Set<ConstraintViolation<GameMap>> violations = validator.validate(gameMap);
//        assertFalse(violations.isEmpty());
//    }
}
