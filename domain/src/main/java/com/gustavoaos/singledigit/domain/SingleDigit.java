package com.gustavoaos.singledigit.domain;

import java.security.InvalidParameterException;

public class SingleDigit {

    private final String n;
    private final String k;

    public SingleDigit(String n, String k) {
        if (Integer.parseInt(n) < 1) {
            throw new InvalidParameterException("N should be greater than 1");
        }
        if (Integer.parseInt(k) < 1) {
            throw new InvalidParameterException("K should be greater than 1");
        }

        this.n = n;
        this.k = k;
    }
}
