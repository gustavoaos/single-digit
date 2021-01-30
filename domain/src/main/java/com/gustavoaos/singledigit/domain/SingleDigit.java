package com.gustavoaos.singledigit.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.InvalidParameterException;

@Embeddable
@NoArgsConstructor
public class SingleDigit implements Serializable {

    private static final Integer MINIMUM_VALUE = 1;
    private static final BigInteger MAXIMUM_VALUE_N = BigInteger.valueOf(10).pow(10^1000000);
    private static final BigInteger MAXIMUM_VALUE_K = BigInteger.valueOf(10).pow(5);

    @Getter private String n;
    @Getter private String k;

    private boolean isLowerThanMinimumValue(BigInteger value) {
        return value.intValue() < MINIMUM_VALUE;
    }

    private boolean isGreaterThanMaximumValue(BigInteger value, BigInteger upperBound) {
        return value.compareTo(upperBound) > 0;
    }

    public SingleDigit(String n, String k) {
        BigInteger nBigInt = new BigInteger(n);
        BigInteger kBigInt = new BigInteger(k);

        if (isLowerThanMinimumValue(nBigInt)) {
            throw new InvalidParameterException("N should be greater or equals to 1");
        }
        if (isLowerThanMinimumValue(kBigInt)) {
            throw new InvalidParameterException("K should be greater or equals to 1");
        }
        if (this.isGreaterThanMaximumValue(nBigInt, MAXIMUM_VALUE_N)) {
            throw new InvalidParameterException("N should be lower than inputted value");
        }
        if (this.isGreaterThanMaximumValue(kBigInt, MAXIMUM_VALUE_K)) {
            throw new InvalidParameterException("K should be lower than inputted value");
        }

        this.n = n;
        this.k = k;
    }

    public int compute() {
        BigInteger bigN = new BigInteger(this.n);
        BigInteger bigK = new BigInteger(this.k);

        return singleDigitSum(bigN, bigK);
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
