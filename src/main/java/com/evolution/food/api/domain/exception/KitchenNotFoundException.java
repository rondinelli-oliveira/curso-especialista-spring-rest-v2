package com.evolution.food.api.domain.exception;

public class KitchenNotFoundException extends EntityNotFoundException {

    public KitchenNotFoundException(String message) {
        super(message);
    }

    public KitchenNotFoundException(Long id) {
        super(String.format("Nao existe um cadastro de cozinha com o codigo: %d", id));
    }
}
