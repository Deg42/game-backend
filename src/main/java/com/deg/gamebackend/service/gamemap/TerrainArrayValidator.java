package com.deg.gamebackend.service.gamemap;

import com.deg.gamebackend.entity.terrain.Terrain;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.Objects;

public class TerrainArrayValidator implements ConstraintValidator<ValidTerrainArray, Terrain[][]> {

    @Override
    public void initialize(ValidTerrainArray constraintAnnotation) {
    }

    @Override
    public boolean isValid(Terrain[][] terrains, ConstraintValidatorContext context) {
        // Check if terrains is null
        if (Objects.isNull(terrains)) {
            return false;
        }

        // Check if terrains has rows and if rows are of the same length
        int rowCount = terrains.length;
        if (rowCount == 0) return false;

        int rowLength = terrains[0].length;

        boolean allRowsEqualSize = Arrays.stream(terrains)
                .allMatch(row -> row.length == rowLength);

        if (!allRowsEqualSize) {
            return false;
        }

        // Check if all elements and their types are non-null
        return Arrays.stream(terrains)
                .flatMap(Arrays::stream)
                .allMatch(terrain -> Objects.nonNull(terrain) && Objects.nonNull(terrain.getType()));

    }
}



