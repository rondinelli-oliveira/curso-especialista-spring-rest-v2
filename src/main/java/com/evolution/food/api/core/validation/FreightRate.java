package com.evolution.food.api.core.validation;

import jakarta.validation.Constraint;
import jakarta.validation.OverridesAttribute;
import jakarta.validation.Payload;
import jakarta.validation.constraints.PositiveOrZero;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = { })
@PositiveOrZero
public @interface FreightRate {

    @OverridesAttribute(constraint = PositiveOrZero.class, name = "message")
    String message() default "{FreightRate.invalid}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
