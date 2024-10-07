package com.evolution.food.api.execeptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    ENTITY_NOT_FOUND("Entity not found.", "/entity-not-found" );

    private String title;
    private String path;

    ProblemType(String title, String path) {
        this.title = title;
        this.path = "https://evolutionfood.com.br" + path;
    }
}
