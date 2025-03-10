package com.evolution.food.api.core.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.BindingResult;

@AllArgsConstructor
@Getter
public class ValidateException extends RuntimeException{

    private BindingResult bindingResult;

}
