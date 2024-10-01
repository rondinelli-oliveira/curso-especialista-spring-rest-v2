package com.evolution.food.api.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

//@ResponseStatus(HttpStatus.NOT_FOUND, reason = "Entidade não encontrada")
public class EntityNotFoundException extends ResponseStatusException {

    public EntityNotFoundException(HttpStatusCode status, String message) {
        super(status, message);
    }

    public EntityNotFoundException(String message) {
        this(HttpStatus.NOT_FOUND, message);
    }
}
