package com.evolution.food.api.domain.exception;

//@ResponseStatus(HttpStatus.CONFLICT)
public class EntityInUseException extends RuntimeException{

    public EntityInUseException(String message) {
        super(message);
    }
}
