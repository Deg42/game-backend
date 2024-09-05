package com.deg.gamebackend.entity.terrain;

import com.deg.gamebackend.service.gamemap.ValidTerrainArray;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "game_map")
public class GameMap {

    @Id
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String id;

    @Schema(description = "Name of the map")
    @NotNull(message = "Name map cannot be null")
    @Size(min = 5, max = 20, message = "Name must be between 5 and 20 characters")
    private String name;

    @Schema(description = "A 2D array of terrains",
            example = "[[{\"type\": \"LAND\"}, {\"type\": \"SEA\"}], [{\"type\": \"LAND\"}, {\"type\": \"SEA\"}]]")
    @NotNull(message = "Terrains array cannot be null")
    @Size(min = 2, message = "Terrains array must have at least two rows")
    @ValidTerrainArray
    private Terrain[][] terrains;

    @JsonCreator
    public GameMap(
            @JsonProperty("name")
            String name,
            @JsonProperty("terrains")
            Terrain[][] terrains) {
        this.name = name;
        this.terrains = terrains;
    }

}
