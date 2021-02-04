package com.gustavoaos.singledigit.domain.exception;

import lombok.Getter;

public class WrongKeyException extends RuntimeException {

    @Getter private final String resourceName;
    @Getter private final String id;

    public WrongKeyException(String resourceName, String id) {
        super("Invalid public key provided");
        this.resourceName = resourceName;
        this.id = id;
    }
}
