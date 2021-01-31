package com.gustavoaos.singledigit.domain;

import com.gustavoaos.singledigit.domain.exception.ArgumentOutOfRangeException;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.math.BigInteger;

@Embeddable
@NoArgsConstructor
public class SingleDigit implements Serializable {

    private static final Integer MINIMUM_VALUE = 1;
    private static final BigInteger MAXIMUM_VALUE_N = BigInteger.valueOf(10).pow(10^1000000);
    private static final BigInteger MAXIMUM_VALUE_K = BigInteger.valueOf(10).pow(5);

    @Getter private String n;
    @Getter private String k;
    @Getter private int result;

    private boolean isLowerThanMinimumValue(BigInteger value) {
        return value.intValue() < MINIMUM_VALUE;
    }

    private boolean isGreaterThanMaximumValue(BigInteger value, BigInteger upperBound) {
        return value.compareTo(upperBound) > 0;
    }

    public SingleDigit(String n, String k) {
        BigInteger nBigInt = new BigInteger(n);
        BigInteger kBigInt = new BigInteger(k);

        if (isLowerThanMinimumValue(nBigInt) || this.isGreaterThanMaximumValue(nBigInt, MAXIMUM_VALUE_N)) {
            throw new ArgumentOutOfRangeException("n", "1", "10ˆ1000000");
        }
        if (isLowerThanMinimumValue(kBigInt) || this.isGreaterThanMaximumValue(kBigInt, MAXIMUM_VALUE_K)) {
            throw new ArgumentOutOfRangeException("k", "1", "10ˆ5");
        }

        this.n = n;
        this.k = k;
        this.result = this.compute();
    }

    private int compute() {
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
