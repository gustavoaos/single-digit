package com.gustavoaos.singledigit.domain;

import com.gustavoaos.singledigit.domain.exception.ArgumentOutOfRangeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SingleDigitTest {

    String n;
    String k;
    String nArgumentOutOfRangeExceptionMessage = "Argument n must be greater than 1 and lower than 10ˆ1000000.";
    String kArgumentOutOfRangeExceptionMessage = "Argument k must be greater than 1 and lower than 10ˆ5.";

    @BeforeEach
    void initEach() {
        n = null;
        k = null;
    }

    @Test
    @DisplayName("Should throws Parameters Out Of Range Exception when N is negative")
    void shouldThrowsParameterOutOfRangeExceptionWhenNIsNegative() {
        n = "-1";
        k = "1";

        assertThatThrownBy(() -> new SingleDigit(n, k))
                .isInstanceOf(ArgumentOutOfRangeException.class)
                .hasMessage(nArgumentOutOfRangeExceptionMessage);

    }

    @Test
    @DisplayName("Should throws Parameter Out Of Range Exception when K is negative")
    void shouldThrowsParameterOutOfRangeExceptionWhenKIsNegative() {
        n = "1";
        k = "-1";

        assertThatThrownBy(() -> new SingleDigit(n, k))
                .isInstanceOf(ArgumentOutOfRangeException.class)
                .hasMessage(kArgumentOutOfRangeExceptionMessage);

    }

    @Test
    @DisplayName("Should throws Parameter Out Of Range Exception when N is zero")
    void shouldThrowsParameterOutOfRangeExceptionWhenNIsZero() {
        n = "0";
        k = "1";

        assertThatThrownBy(() -> new SingleDigit(n, k))
                .isInstanceOf(ArgumentOutOfRangeException.class)
                .hasMessage(nArgumentOutOfRangeExceptionMessage);

    }

    @Test
    @DisplayName("Should throws Parameter Out Of Range Exception when K is zero")
    void shouldThrowsParameterOutOfRangeExceptionWhenKIsZero() {
        n = "1";
        k = "0";

        assertThatThrownBy(() -> new SingleDigit(n, k))
                .isInstanceOf(ArgumentOutOfRangeException.class)
                .hasMessage(kArgumentOutOfRangeExceptionMessage);

    }

    @Test
    @DisplayName("Should throws Number Format Exception when N is invalid string")
    void shouldThrowsNumberFormatExceptionWhenNIsInvalidString() {
        n = "invalid_value";
        k = "1";

        assertThatThrownBy(() -> new SingleDigit(n, k))
                .isInstanceOf(NumberFormatException.class);
    }

    @Test
    @DisplayName("Should throws an Number Format Exception when K is invalid string")
    void shouldThrowsNumberFormatExceptionWhenKIsInvalidString() {
        n = "1";
        k = "invalid_value";

        assertThatThrownBy(() -> new SingleDigit(n, k))
                .isInstanceOf(NumberFormatException.class);

    }

    @Test
    @Disabled("Slow test: tests N upper bound limit")
    @DisplayName("Should throws Parameter Out Of Range Exception if n is greater than maximum value")
    void shouldThrowsParameterOutOfRangeExceptionIfNIsGreaterThanMaximumValue() {
        BigInteger max = BigInteger.valueOf(10).pow(10^1000000);
        String n = max.add(BigInteger.ONE).toString();
        k = "1";

        assertThatThrownBy(() -> new SingleDigit(n, k))
                .isInstanceOf(ArgumentOutOfRangeException.class)
                .hasMessage(nArgumentOutOfRangeExceptionMessage);
    }

    @Test
    @DisplayName("Should throws Parameter Out Of Range Exception if k is greater than maximum value")
    void shouldThrowsParameterOutOfRangeExceptionIfKIsGreaterThanMaximumValue() {
        BigInteger max = BigInteger.valueOf(10).pow(5);
        String k = max.add(BigInteger.ONE).toString();
        n = "1";

        assertThatThrownBy(() -> new SingleDigit(n, k))
                .isInstanceOf(ArgumentOutOfRangeException.class)
                .hasMessage(kArgumentOutOfRangeExceptionMessage);
    }

    @Test
    @DisplayName("Should returns n when n is lower than 10 and K is equals to 1")
    void shouldReturnsNWhenNIsLowerThanTenAndKIsEqualsToOne() {
        SingleDigit stu = new SingleDigit("7", "1");
        int expected = 7;

        assertThat(stu.getResult()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Should compute single digit when n is greater than 10 and K is equals to 1")
    void shouldComputeSingleDigitWhenNIsGreaterThanTen() {
        SingleDigit stu = new SingleDigit("16", "1");
        int expected = 7;

        assertThat(stu.getResult()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Should compute single digit when n and k are greater than 10")
    void shouldComputeSingleDigitWhenNAndKAreGreaterThanTen() {
        SingleDigit stu = new SingleDigit("9875", "4");
        int expected = 8;

        assertThat(stu.getResult()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Should compute single digit when mod times k is greater than 10")
    void shouldComputeSingleDigitWhenModTimesKIsGreaterThanTen() {
        SingleDigit stu = new SingleDigit("9881", "4");
        int expected = 5;

        assertThat(stu.getResult()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Should compute single digit when mod is equals to 9")
    void shouldComputeSingleDigitWhenModIsEqualsToNine() {
        SingleDigit stu = new SingleDigit("9", "1");
        int expected = 9;

        assertThat(stu.getResult()).isEqualTo(expected);
    }
}
