package com.deg.gamebackend.service;

import com.deg.gamebackend.entity.terrain.GameMap;
import com.deg.gamebackend.repository.GameMapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameMapService {

    private final GameMapRepository gameMapRepository;

    @Autowired
    public GameMapService(GameMapRepository gameMapRepository) {
        this.gameMapRepository = gameMapRepository;
    }

    public GameMap save(GameMap gameMap) {
        return gameMapRepository.save(gameMap);
    }

    public Optional<GameMap> findById(String id) {
        return gameMapRepository.findById(id);
    }

    public List<GameMap> findAll() {
        return gameMapRepository.findAll();
    }
}