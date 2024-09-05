package com.deg.gamebackend.controller;

import com.deg.gamebackend.entity.terrain.GameMap;
import com.deg.gamebackend.service.gamemap.GameMapService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/gamemaps")
public class GameMapController {

    private final GameMapService gameMapService;

    @Autowired
    public GameMapController(GameMapService gameMapService) {
        this.gameMapService = gameMapService;
    }

    @PostMapping
    public ResponseEntity createGameMap(@RequestBody GameMap gameMap) {
        try {
            GameMap savedGameMap = gameMapService.save(gameMap);
            return new ResponseEntity<>(savedGameMap, HttpStatus.CREATED);
        } catch (ValidationException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameMap> getGameMapById(@PathVariable String id) {
        Optional<GameMap> gameMap = gameMapService.findById(id);
        return gameMap.map(map -> new ResponseEntity<>(map, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<GameMap>> getAllGameMaps() {
        List<GameMap> gameMaps = gameMapService.findAll();
        return new ResponseEntity<>(gameMaps, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGameMapById(@PathVariable String id) {
        gameMapService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteAllGameMaps() {
        gameMapService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}