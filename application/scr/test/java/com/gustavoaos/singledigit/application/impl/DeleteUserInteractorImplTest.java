package com.gustavoaos.singledigit.application.impl;

import com.gustavoaos.singledigit.domain.User;
import com.gustavoaos.singledigit.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@SpringBootTest
class DeleteUserInteractorImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DeleteUserInteractorImpl sut;

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
        when(userRepository.findById(uuid)).thenReturn(Optional.of(user));
    }

    @Test
    @Description("Should delete an user when valid id is provided")
    void shouldDeleteAnUserWhenValidIdIsProvided() {
        doNothing().when(userRepository).delete(user);

        sut.execute(uuid.toString());

        verify(userRepository, times(1)).delete(user);
    }
}
