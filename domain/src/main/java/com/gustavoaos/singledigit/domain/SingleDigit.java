package com.gustavoaos.singledigit.domain;

import java.math.BigInteger;
import java.security.InvalidParameterException;

public class SingleDigit {

    private static final Integer MINIMUM_VALUE = 1;
    private static final BigInteger MAXIMUM_VALUE_N = BigInteger.valueOf(10).pow(10^1000000);

    private final String n;
    private final String k;

    private boolean isLowerThanMinimumValue(String s) {
        BigInteger value = new BigInteger(s);

        return value.intValue() < MINIMUM_VALUE;
    }

    private boolean isGreaterThanMaximumValue(String s, BigInteger upperBound) {
        BigInteger value = new BigInteger(s);

        return value.compareTo(upperBound) > 0;
    }

    public SingleDigit(String n, String k) {
        if (isLowerThanMinimumValue(n)) {
            throw new InvalidParameterException("N should be greater or equals to 1");
        }
        if (isLowerThanMinimumValue(k)) {
            throw new InvalidParameterException("K should be greater or equals to 1");
        }
        if (this.isGreaterThanMaximumValue(n, MAXIMUM_VALUE_N)) {
            throw new InvalidParameterException("N should be lower than inputted value");
        }

        this.n = n;
        this.k = k;
    }
}
