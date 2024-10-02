package com.evolution.food.api.domain.exception;

public class StateNotFoundException extends EntityNotFoundException {

    public StateNotFoundException(String message) {
        super(message);
    }

    public StateNotFoundException(Long id) {
        super(String.format("Nao existe um cadastro de estado com o codigo: %d", id));
    }
}
