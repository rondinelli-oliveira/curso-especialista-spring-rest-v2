package com.evolution.food.api.execeptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    RESOURCE_NOT_FOUND("Resource not found.", "/resource-not-found" ),
    ENTITY_IN_USE("Entity in use.", "/entity-in-use"),
    BUSINESS_ERROR("Business rule violation.", "/business-error"),
    INCOMPATIBLE_MESSAGE("Incompatible message.", "/incompatible-message"),
    INVALID_PARAMETER("Invalid parameter", "/invalid-parameter"),
    SYSTEM_ERROR("System error", "/system-error"),
    INVALID_DATA("Invalid data", "/invalid-data")
    ;

    private String title;
    private String path;

    ProblemType(String title, String path) {
        this.title = title;
        this.path = "https://evolutionfood.com.br" + path;
    }
}
