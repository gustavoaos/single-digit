package com.gustavoaos.singledigit.domain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserTest {

    @Test
    @Description("Should returns a single digits empty list when creating an user")
    void shouldReturnsASingleDigitEmptyListWhenCreatingUser() {
        User sut = User.builder().name("Joe Doe").email("Joe Doe").build();

        assertThat(sut.getSingleDigits().size()).isZero();
    }

}
