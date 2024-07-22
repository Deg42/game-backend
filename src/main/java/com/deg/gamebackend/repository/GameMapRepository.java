package com.deg.gamebackend.repository;

import com.deg.gamebackend.entity.terrain.GameMap;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameMapRepository extends MongoRepository<GameMap, String> {
}