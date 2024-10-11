package com.evolution.food.api.execeptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    ENTITY_NOT_FOUND("Entity not found.", "/entity-not-found" ),
    ENTITY_IN_USE("Entity in use.", "/entity-in-use"),
    BUSINESS_ERROR("Business rule violation.", "/business-error"),
    INCOMPATIBLE_MESSAGE("Incompatible message.", "/incompatible-message"),
    INVALID_PARAMETER("Invalid parameter", "/invalid-parameter");

    private String title;
    private String path;

    ProblemType(String title, String path) {
        this.title = title;
        this.path = "https://evolutionfood.com.br" + path;
    }
}
