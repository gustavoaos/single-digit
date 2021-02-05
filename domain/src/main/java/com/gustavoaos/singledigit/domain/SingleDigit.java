package com.gustavoaos.singledigit.domain;

import com.gustavoaos.singledigit.domain.exception.ArgumentOutOfRangeException;
import com.gustavoaos.singledigit.domain.strategy.ComputeStrategy;
import com.gustavoaos.singledigit.domain.strategy.SingleDigitStrategy;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.math.BigInteger;

@Embeddable
@NoArgsConstructor
public class SingleDigit {

    private static final BigInteger MINIMUM_VALUE = BigInteger.ONE;
    private static final BigInteger MAXIMUM_VALUE_N = BigInteger.valueOf(10).pow(10^1_000_000);
    private static final BigInteger MAXIMUM_VALUE_K = BigInteger.valueOf(10).pow(5);

    @Getter private String n;
    @Getter private String k;
    @Getter private int result;

    @Transient
    private SingleDigitStrategy strategy;

    private boolean isLowerThanMinimumValue(BigInteger value) {
        return value.compareTo(MINIMUM_VALUE) < 0;
    }

    private boolean isGreaterThanMaximumValue(BigInteger value, BigInteger upperBound) {
        return value.compareTo(upperBound) > 0;
    }

    public SingleDigit(String n, String k, SingleDigitStrategy strategy) {
        validateConstrains(n, k);

        this.n = n;
        this.k = k;
        this.strategy = strategy;
        this.result = this.strategy.compute(n, k);
    }

    private void validateConstrains(String n, String k) {
        BigInteger bigN = new BigInteger(n);
        BigInteger bigK = new BigInteger(k);

        if (isLowerThanMinimumValue(bigN) || this.isGreaterThanMaximumValue(bigN, MAXIMUM_VALUE_N)) {
            throw new ArgumentOutOfRangeException("n", "1", "10ˆ1000000");
        }
        if (isLowerThanMinimumValue(bigK) || this.isGreaterThanMaximumValue(bigK, MAXIMUM_VALUE_K)) {
            throw new ArgumentOutOfRangeException("k", "1", "10ˆ5");
        }
    }
}
