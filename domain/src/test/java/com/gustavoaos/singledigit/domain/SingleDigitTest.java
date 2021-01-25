package com.gustavoaos.singledigit.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.InvalidParameterException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class SingleDigitTest {

    String n = "";
    String k = "";

    @Test
    @DisplayName("Should throws an Invalid Parameter Exception when N is negative")
    void shouldThrowsInvalidParameterExceptionWhenNIsNegative() {
        n = "-1";
        k = "1";

        assertThatThrownBy(() -> new SingleDigit(n, k))
                .isInstanceOf(InvalidParameterException.class)
                .hasMessage("N should be greater or equals to 1");

    }

    @Test
    @DisplayName("Should throws an Invalid Parameter Exception when K is negative")
    void shouldThrowsInvalidParameterExceptionWhenKIsNegative() {
        n = "1";
        k = "-1";

        assertThatThrownBy(() -> new SingleDigit(n, k))
                .isInstanceOf(InvalidParameterException.class)
                .hasMessage("K should be greater or equals to 1");

    }

    @Test
    @DisplayName("Should throws an Invalid Parameter Exception when N is zero")
    void shouldThrowsInvalidParameterExceptionWhenNIsZero() {
        n = "0";
        k = "1";

        assertThatThrownBy(() -> new SingleDigit(n, k))
                .isInstanceOf(InvalidParameterException.class)
                .hasMessage("N should be greater or equals to 1");

    }

    @Test
    @DisplayName("Should throws an Invalid Parameter Exception when K is zero")
    void shouldThrowsInvalidParameterExceptionWhenKIsZero() {
        n = "1";
        k = "0";

        assertThatThrownBy(() -> new SingleDigit(n, k))
                .isInstanceOf(InvalidParameterException.class)
                .hasMessage("K should be greater or equals to 1");

    }

    @Test
    @DisplayName("Should throws an Invalid Parameter Exception when N is invalid string")
    void shouldThrowsNumberFormatExceptionWhenNIsInvalidString() {
        n = "invalid_value";
        k = "1";

        assertThatThrownBy(() -> new SingleDigit(n, k))
                .isInstanceOf(NumberFormatException.class);

    }
}
