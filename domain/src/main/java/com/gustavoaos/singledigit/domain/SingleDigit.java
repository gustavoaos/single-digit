package com.gustavoaos.singledigit.domain;

import com.gustavoaos.singledigit.domain.exception.ArgumentOutOfRangeException;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
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

    private boolean isLowerThanMinimumValue(BigInteger value) {
        return value.compareTo(MINIMUM_VALUE) < 0;
    }

    private boolean isGreaterThanMaximumValue(BigInteger value, BigInteger upperBound) {
        return value.compareTo(upperBound) > 0;
    }

    public SingleDigit(String n, String k) {
        validateConstrains(n, k);

        this.n = n;
        this.k = k;
        this.result = this.compute();
    }

    public SingleDigit(String n, String k, Integer result) {
        validateConstrains(n, k);

        this.n = n;
        this.k = k;
        this.result = result;
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
            return singleDigitSum(sum, BigInteger.valueOf(1));
        }

        return sum.intValue();
    }
}
