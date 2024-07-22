package com.deg.gamebackend.controller.validators;
import com.deg.gamebackend.entity.terrain.Terrain;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RectangularTerrainMatrixValidator implements ConstraintValidator<ValidRectangularTerrainMatrix, Terrain[][]> {

    @Override
    public boolean isValid(Terrain[][] value, ConstraintValidatorContext context) {
        if (value == null || value.length == 0) return false;

        int rowLength = value[0].length;
        for (Terrain[] row : value) {
            if (row == null || row.length != rowLength) return false;
            for (Terrain terrain : row) {
                if (terrain == null || terrain.getType() == null) return false;
            }
        }
        return true;
    }
}
