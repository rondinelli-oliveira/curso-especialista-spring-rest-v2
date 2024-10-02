package com.evolution.food.api.domain.exception;

public class CityNotFoundException extends EntityNotFoundException {

    public CityNotFoundException(String message) {
        super(message);
    }

    public CityNotFoundException(Long id) {
        super(String.format("Nao existe um cadastro de cidade com o codigo: %d", id));
    }
}
