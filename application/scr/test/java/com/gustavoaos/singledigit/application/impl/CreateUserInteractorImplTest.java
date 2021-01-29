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
    @Description("Should return an UserResponse when valid UserRequest is provided")
    void shouldReturnAnUserResponseWhenValidUserRequestIsProvided() {
        UserResponse response = UserResponse.builder().id(uuid.toString()).name(name).email(email).build();
        CreateUserRequest request = CreateUserRequest.builder().name(name).email(email).build();

        assertThat(sut.execute(request)).isEqualTo(response);
    }

}
