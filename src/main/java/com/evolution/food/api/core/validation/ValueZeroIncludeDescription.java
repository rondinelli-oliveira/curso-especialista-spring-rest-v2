package com.evolution.food.api.core.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = {ValueZeroIncludeDescriptionValidator.class })
public @interface ValueZeroIncludeDescription {

    String message() default "{descricao obrigatoria invalida}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    String valueField();

    String descriptionField();

    String requiredDescription();
}
