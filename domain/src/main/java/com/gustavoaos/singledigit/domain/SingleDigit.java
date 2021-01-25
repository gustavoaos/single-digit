package com.gustavoaos.singledigit.domain;

import java.security.InvalidParameterException;

public class SingleDigit {

    private static final Integer MINIMUM_VALUE = 1;

    private final String n;
    private final String k;

    private boolean isLowerThanMinimumValue(String s) {
        return Integer.parseInt(s) < MINIMUM_VALUE;
    }

    public SingleDigit(String n, String k) {
        if (isLowerThanMinimumValue(n)) {
            throw new InvalidParameterException("N should be greater or equals to 1");
        }
        if (isLowerThanMinimumValue(k)) {
            throw new InvalidParameterException("K should be greater or equals to 1");
        }

        this.n = n;
        this.k = k;
    }
}
