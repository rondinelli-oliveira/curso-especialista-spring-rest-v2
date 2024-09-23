package com.evolution.food.api.domain.exception;

public class EntityInUseException extends RuntimeException{

    public EntityInUseException(String message) {
        super(message);
    }
}
