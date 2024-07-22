package com.deg.gamebackend.controller.validators;

import com.deg.gamebackend.entity.terrain.GameMap;

import javax.validation.*;
import java.util.Set;

public class GameMapValidator {

    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = factory.getValidator();

    public static void validateGameMap(GameMap gameMap) throws ValidationException {
        Set<ConstraintViolation<GameMap>> violations = validator.validate(gameMap);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<GameMap> violation : violations) {
                sb.append(violation.getMessage()).append("\n");
            }
            throw new ValidationException(sb.toString());
        }
    }
}

