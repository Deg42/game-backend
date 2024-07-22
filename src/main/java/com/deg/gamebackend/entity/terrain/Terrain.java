package com.deg.gamebackend.entity.terrain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Data
public class Terrain {

    @NotNull(message = "Terrain type cannot be null")
    private TerrainType type;

}
