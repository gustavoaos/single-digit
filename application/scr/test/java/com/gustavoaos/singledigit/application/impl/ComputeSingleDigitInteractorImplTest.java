package com.gustavoaos.singledigit.application.impl;

import com.gustavoaos.singledigit.domain.User;
import com.gustavoaos.singledigit.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class ComputeSingleDigitInteractorImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ComputeSingleDigitInteractorImpl sut;

    private User user;
    private UUID uuid;
    private String name;
    private String email;

    @BeforeEach
    void setMockOutput() {
        uuid = UUID.randomUUID();
        name = "Joe Doe";
        email = "joe@doe.com";

        user = User.builder().uuid(uuid).name(name).email(email).build();
        when(userRepository.save(any())).thenReturn(user);
    }

    @Test
    @Description("Should throw an IllegalArgumentException when request is null")
    void shouldThrowAnIllegalArgumentExceptionWhenRequestIsNull() {
        assertThatThrownBy(() -> sut.execute(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Missing argument of type ComputeSingleDigitRequest");
    }

}
