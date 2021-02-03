package com.gustavoaos.singledigit.domain.strategy;

import java.math.BigInteger;

public class ComputeStrategy implements SingleDigitStrategy {

    @Override
    public int compute(String n, String k) {
        BigInteger bigN = new BigInteger(n);
        BigInteger bigK = new BigInteger(k);

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
