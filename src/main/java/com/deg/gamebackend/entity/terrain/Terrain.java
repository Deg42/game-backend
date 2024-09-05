package com.deg.gamebackend.entity.terrain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Represents a terrain type in the game map")
public class Terrain {

    @Schema(description = "The type of the terrain", example = "LAND")
    private TerrainType type;

    @JsonCreator
    public Terrain(
            @JsonProperty("type")
            TerrainType type) {
        this.type = type;
    }
}
