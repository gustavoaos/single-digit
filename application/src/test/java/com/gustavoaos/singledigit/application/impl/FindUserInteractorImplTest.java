package com.gustavoaos.singledigit.application.impl;

import com.gustavoaos.singledigit.application.response.UserResponse;
import com.gustavoaos.singledigit.domain.User;
import com.gustavoaos.singledigit.domain.exception.NotFoundException;
import com.gustavoaos.singledigit.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
class FindUserInteractorImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private FindUserInteractorImpl sut;

    private UUID uuid;
    private String name;
    private String email;

    @BeforeEach
    void setMockOutput() {
        uuid = UUID.randomUUID();
        name = "Joe Doe";
        email = "joe@doe.com";

        User user = User.builder().uuid(uuid).name(name).email(email).build();
        when(userRepository.findById(uuid)).thenReturn(Optional.of(user));
    }

    @Test
    @Description("Should return an UserResponse when valid id is provided")
    void shouldReturnAnUserResponseWhenValidIdIsProvided() {
        UserResponse response = UserResponse.builder().id(uuid.toString()).name(name).email(email).build();

        assertThat(sut.execute(uuid.toString())).isEqualTo(response);
    }

    @Test
    @Description("Should throw a NotFoundException when invalid id is provided")
    void shouldThrowANotFoundExceptionWhenInValidIdIsProvided() {
        String invalidId = uuid.toString();

        when(userRepository.findById(uuid)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> sut.execute(invalidId))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Resource not found");
    }

    @Test
    @Description("Should throw an IllegalArgumentException when invalid UUID is provided")
    void shouldThrowAnIllegalArgumentExceptionWhenInValidUuidIsProvided() {
        String invalidUuid = "123L";

        assertThatThrownBy(() -> sut.execute(invalidUuid))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid UUID string: " + invalidUuid);
    }

}
