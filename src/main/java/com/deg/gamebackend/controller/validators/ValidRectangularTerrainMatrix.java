package com.deg.gamebackend.controller.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = RectangularTerrainMatrixValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidRectangularTerrainMatrix {
    String message() default "Terrains matrix must be rectangular and not contain empty terrains";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
