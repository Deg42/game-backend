package com.deg.gamebackend.service.gamemap;

import com.deg.gamebackend.entity.terrain.GameMap;
import com.deg.gamebackend.repository.GameMapRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class GameMapService {

    private final GameMapRepository gameMapRepository;
    private Validator validator;

    @Autowired
    public GameMapService(GameMapRepository gameMapRepository, Validator validator) {
        this.gameMapRepository = gameMapRepository;
        this.validator = validator;
    }

    public GameMap save(GameMap gameMap) throws ValidationException {
        Set<ConstraintViolation<GameMap>> violations = validator.validate(gameMap);
        if (!violations.isEmpty()) {
            StringBuilder violationMessages = new StringBuilder("Validation failed: ");
            for (ConstraintViolation<GameMap> violation : violations) {
                violationMessages.append(violation.getPropertyPath()).append(" ")
                        .append(violation.getMessage()).append("; ");
            }
            throw new ValidationException(violationMessages.toString());
        }
        return gameMapRepository.save(gameMap);
    }

    public Optional<GameMap> findById(String id) {
        return gameMapRepository.findById(id);
    }

    public List<GameMap> findAll() {
        return gameMapRepository.findAll();
    }

    public void deleteById(String id) {
        gameMapRepository.deleteById(id);
    }

    public void deleteAll() {
        gameMapRepository.deleteAll();
    }
}