package com.gustavoaos.singledigit.domain.exception;

import lombok.Getter;

public class NotFoundException extends RuntimeException {

    @Getter private final String resourceName;
    @Getter private final String id;

    public NotFoundException(String resourceName, String id) {
        super("Resource not found");
        this.resourceName = resourceName;
        this.id = id;
    }
}
