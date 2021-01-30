package com.gustavoaos.singledigit.application.impl;

import com.gustavoaos.singledigit.application.request.CreateUserRequest;
import com.gustavoaos.singledigit.application.response.UserResponse;
import com.gustavoaos.singledigit.domain.User;
import com.gustavoaos.singledigit.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CreateUserInteractorImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CreateUserInteractorImpl sut;

    private UUID uuid;
    private String name;
    private String email;
    private UserResponse response;

    @BeforeEach
    void setMockOutput() {
        uuid = UUID.randomUUID();
        name = "Joe Doe";
        email = "joe@doe.com";

        User user = User.builder().uuid(uuid).name(name).email(email).build();
        response = UserResponse.builder().id(uuid.toString()).name(name).email(email).build();

        when(userRepository.save(any())).thenReturn(user);

        // Mockito does not support mocking static methods
        // when(UserResponse.from(user)).thenReturn(response);
    }

    @Test
    @Description("Should return an UserResponse when valid UserRequest is provided")
    void shouldReturnAnUserResponseWhenValidUserRequestIsProvided() {
        CreateUserRequest request = CreateUserRequest.builder().name(name).email(email).build();

        assertThat(sut.execute(request)).isEqualTo(response);
    }

    @Test
    @Description("Should throw an IllegalArgumentException when request is null")
    void shouldThrowAnIllegalArgumentExceptionWhenRequestIsNull() {
        assertThatThrownBy(() -> sut.execute(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Missing argument of type CreateUserRequest");
    }

}
