package com.gustavoaos.singledigit.domain;

import com.gustavoaos.singledigit.domain.exception.ArgumentOutOfRangeException;
import com.gustavoaos.singledigit.domain.strategy.ComputeStrategy;
import com.gustavoaos.singledigit.domain.strategy.SingleDigitStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class SingleDigitTest {

    private final String nArgumentOutOfRangeExceptionMessage = "Argument n must be greater than 1 and lower than 10ˆ1000000.";
    private final String kArgumentOutOfRangeExceptionMessage = "Argument k must be greater than 1 and lower than 10ˆ5.";
    private SingleDigitStrategy strategy;

    @BeforeEach
    void initEach() {
        strategy = new ComputeStrategy();
    }

    @Test
    @DisplayName("Should throws Parameters Out Of Range Exception when N is negative")
    void shouldThrowsParameterOutOfRangeExceptionWhenNIsNegative() {
        assertThatThrownBy(() -> new SingleDigit("-1", "1", strategy))
                .isInstanceOf(ArgumentOutOfRangeException.class)
                .hasMessage(nArgumentOutOfRangeExceptionMessage);

    }

    @Test
    @DisplayName("Should throws Parameter Out Of Range Exception when K is negative")
    void shouldThrowsParameterOutOfRangeExceptionWhenKIsNegative() {
        assertThatThrownBy(() -> new SingleDigit("1", "-1", strategy))
                .isInstanceOf(ArgumentOutOfRangeException.class)
                .hasMessage(kArgumentOutOfRangeExceptionMessage);

    }

    @Test
    @DisplayName("Should throws Parameter Out Of Range Exception when N is zero")
    void shouldThrowsParameterOutOfRangeExceptionWhenNIsZero() {
        assertThatThrownBy(() -> new SingleDigit("0", "1", strategy))
                .isInstanceOf(ArgumentOutOfRangeException.class)
                .hasMessage(nArgumentOutOfRangeExceptionMessage);

    }

    @Test
    @DisplayName("Should throws Parameter Out Of Range Exception when K is zero")
    void shouldThrowsParameterOutOfRangeExceptionWhenKIsZero() {
        assertThatThrownBy(() -> new SingleDigit("1", "0", strategy))
                .isInstanceOf(ArgumentOutOfRangeException.class)
                .hasMessage(kArgumentOutOfRangeExceptionMessage);

    }

    @Test
    @DisplayName("Should throws Number Format Exception when N is invalid string")
    void shouldThrowsNumberFormatExceptionWhenNIsInvalidString() {
        assertThatThrownBy(() -> new SingleDigit("invalid_value", "1", strategy))
                .isInstanceOf(NumberFormatException.class);
    }

    @Test
    @DisplayName("Should throws an Number Format Exception when K is invalid string")
    void shouldThrowsNumberFormatExceptionWhenKIsInvalidString() {
        assertThatThrownBy(() -> new SingleDigit("1", "invalid_value", strategy))
                .isInstanceOf(NumberFormatException.class);

    }

    @Test
    @Disabled("Slow test: tests N upper bound limit")
    @DisplayName("Should throws Parameter Out Of Range Exception if n is greater than maximum value")
    void shouldThrowsParameterOutOfRangeExceptionIfNIsGreaterThanMaximumValue() {
        BigInteger max = BigInteger.valueOf(10).pow(10^1_000_000);
        String n = max.add(BigInteger.ONE).toString();

        assertThatThrownBy(() -> new SingleDigit(n, "1", strategy))
                .isInstanceOf(ArgumentOutOfRangeException.class)
                .hasMessage(nArgumentOutOfRangeExceptionMessage);
    }

    @Test
    @DisplayName("Should throws Parameter Out Of Range Exception if k is greater than maximum value")
    void shouldThrowsParameterOutOfRangeExceptionIfKIsGreaterThanMaximumValue() {
        BigInteger max = BigInteger.valueOf(10).pow(5);
        String k = max.add(BigInteger.ONE).toString();

        assertThatThrownBy(() -> new SingleDigit("1", k, strategy))
                .isInstanceOf(ArgumentOutOfRangeException.class)
                .hasMessage(kArgumentOutOfRangeExceptionMessage);
    }

    @Test
    @Disabled("Slow test: tests computes single digit when N and K are the maximum value")
    @DisplayName("Should computes single digit when n and k are really big numbers")
    void shouldComputesSingleDigitWhenNAndKAreReallyBigNumbers() {
        String n = BigInteger.valueOf(10).pow(10^1_000_000).toString();
        String k = BigInteger.valueOf(10).pow(5).toString();

        assertThatNoException().isThrownBy(() -> new SingleDigit(n, k, strategy));
        assertThat(new SingleDigit(n, k, strategy).getResult()).isEqualTo(1);
    }

    @Test
    @DisplayName("Should returns n when n is lower than 10 and K is equals to 1")
    void shouldReturnsNWhenNIsLowerThanTenAndKIsEqualsToOne() {
        SingleDigit stu = new SingleDigit("7", "1", strategy);
        int expected = 7;

        assertThat(stu.getResult()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Should compute single digit when n is greater than 10 and K is equals to 1")
    void shouldComputeSingleDigitWhenNIsGreaterThanTen() {
        SingleDigit stu = new SingleDigit("16", "1", strategy);
        int expected = 7;

        assertThat(stu.getResult()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Should compute single digit when n and k are greater than 10")
    void shouldComputeSingleDigitWhenNAndKAreGreaterThanTen() {
        SingleDigit stu = new SingleDigit("9875", "4", strategy);
        int expected = 8;

        assertThat(stu.getResult()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Should compute single digit when mod times k is greater than 10")
    void shouldComputeSingleDigitWhenModTimesKIsGreaterThanTen() {
        SingleDigit stu = new SingleDigit("9881", "4", strategy);
        int expected = 5;

        assertThat(stu.getResult()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Should compute single digit when mod is equals to 9")
    void shouldComputeSingleDigitWhenModIsEqualsToNine() {
        SingleDigit stu = new SingleDigit("9", "1", strategy);
        int expected = 9;

        assertThat(stu.getResult()).isEqualTo(expected);
    }

}
