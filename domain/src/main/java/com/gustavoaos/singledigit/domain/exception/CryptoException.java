package com.gustavoaos.singledigit.domain.exception;

import lombok.Getter;

public class CryptoException extends RuntimeException {

    @Getter private final String resourceName;

    public CryptoException(String resourceName) {
        super("Failed to encrypt");
        this.resourceName = resourceName;
    }
}
