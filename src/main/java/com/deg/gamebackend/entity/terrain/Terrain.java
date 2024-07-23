package com.deg.gamebackend.entity.terrain;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Data
public class Terrain {

    @NotNull(message = "Terrain type cannot be null")
    private TerrainType type;

}
