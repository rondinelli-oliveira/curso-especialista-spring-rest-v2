package com.evolution.food.api.domain.exception;

//@ResponseStatus(HttpStatus.NOT_FOUND)
public abstract class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(String message) {
        super(message);
    }

}
