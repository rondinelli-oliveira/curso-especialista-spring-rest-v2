package com.evolution.food.api.domain.exception;

public class RestaurantNotFoundException extends EntityNotFoundException {

    public RestaurantNotFoundException(String message) {
        super(message);
    }

    public RestaurantNotFoundException(Long id) {
        super(String.format("Nao existe um cadastro de restaurante com o codigo: %d", id));
    }
}
