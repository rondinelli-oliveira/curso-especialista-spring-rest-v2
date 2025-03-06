package com.evolution.food.api.core.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ValidationException;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

public class ValueZeroIncludeDescriptionValidator implements ConstraintValidator<ValueZeroIncludeDescription, Object> {

    private String valueField;

    private String descriptionField;

    private String requiredDescription;

    @Override
    public void initialize(ValueZeroIncludeDescription constraintAnnotation) {
        this.valueField = constraintAnnotation.valueField();
        this.descriptionField = constraintAnnotation.descriptionField();
        this.requiredDescription = constraintAnnotation.requiredDescription();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        boolean valid = true;

        try {
            BigDecimal value = (BigDecimal) BeanUtils.getPropertyDescriptor(object.getClass(), valueField)
                    .getReadMethod().invoke(object);

            String description = (String) BeanUtils.getPropertyDescriptor(object.getClass(), descriptionField)
                    .getReadMethod().invoke(object);

            if (value != null && BigDecimal.ZERO.compareTo(value) == 0 && description != null) {
                valid = description.toLowerCase().contains(this.requiredDescription.toLowerCase());
            }
        } catch (Exception e) {
            throw new ValidationException(e);
        }

        return valid;
    }
}
