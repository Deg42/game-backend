package com.deg.gamebackend.entity.terrain;

import com.deg.gamebackend.controller.validators.ValidRectangularTerrainMatrix;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@Document(collection = "game_map")
public class GameMap {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameMap.class);

    @Id
    private String id;
    @NotNull(message = "Terrains array cannot be null")
    @Size(min = 1, message = "Terrains array must have at least one row")
    @ValidRectangularTerrainMatrix
    @Valid
    private Terrain[][] terrains;

}
