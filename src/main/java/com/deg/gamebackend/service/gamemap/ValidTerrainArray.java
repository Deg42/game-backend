package com.deg.gamebackend.service.gamemap;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TerrainArrayValidator.class)
public @interface ValidTerrainArray {
    String message() default "Invalid terrain array";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
