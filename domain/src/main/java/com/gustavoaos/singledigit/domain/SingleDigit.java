package com.gustavoaos.singledigit.domain;

import java.math.BigInteger;
import java.security.InvalidParameterException;

public class SingleDigit {

    private static final Integer MINIMUM_VALUE = 1;
    private static final BigInteger MAXIMUM_VALUE_N = BigInteger.valueOf(10).pow(10^1000000);
    private static final BigInteger MAXIMUM_VALUE_K = BigInteger.valueOf(10).pow(5);

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
        if (this.isGreaterThanMaximumValue(k, MAXIMUM_VALUE_K)) {
            throw new InvalidParameterException("K should be lower than inputted value");
        }

        this.n = n;
        this.k = k;
    }

    public int calculate() {
        BigInteger n = new BigInteger(this.n);
        BigInteger k = new BigInteger(this.k);

        return singleDigitSum(n, k);
    }

    private int singleDigitSum(BigInteger n, BigInteger k) {
        BigInteger bigNine = BigInteger.valueOf(9);
        BigInteger mod = n.mod(bigNine).compareTo(BigInteger.ZERO) == 0 ? bigNine : n.mod(bigNine);
        BigInteger sum = mod.multiply(k);

        if (sum.compareTo(BigInteger.TEN) > 0) {
            return singleDigitSum(sum, k);
        }

        return sum.intValue();
    }
}
