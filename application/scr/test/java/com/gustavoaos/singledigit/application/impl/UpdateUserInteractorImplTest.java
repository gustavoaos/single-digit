package com.gustavoaos.singledigit.application.impl;

import com.gustavoaos.singledigit.application.request.UpdateUserRequest;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class UpdateUserInteractorImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UpdateUserInteractorImpl sut;

    private UUID uuid;
    private String updatedName;
    private String updatedEmail;
    private UpdateUserRequest updatedUser;

    @BeforeEach
    void setMockOutput() {
        String name = "Joe Doe";
        String email = "joe@doe.com";
        uuid = UUID.randomUUID();
        updatedName = "Updated Name";
        updatedEmail = "updated@mail.com";

        User user = User.builder().uuid(uuid).name(name).email(email).build();
        updatedUser = UpdateUserRequest.builder().name(updatedName).email(updatedEmail).build();

        when(userRepository.findById(uuid)).thenReturn(Optional.of(user));
        when(userRepository.save(any())).thenReturn(user);
    }

    @Test
    @Description("Should return an updated User when valid id is provided")
    void shouldReturnAnUpdatedUserResponseWhenValidIdIsProvided() {
        UserResponse response = UserResponse
                .builder().id(uuid.toString()).name(updatedName).email(updatedEmail).build();

        assertThat(sut.execute(
                uuid.toString(),
                updatedUser
        )).isEqualTo(response);
    }

    @Test
    @Description("Should throw NotFoundException when invalid id is provided")
    void shouldThrowNotFoundExceptionWhenInvalidIdIsProvided() {
        String invalidId = uuid.toString();
        when(userRepository.findById(uuid)).thenThrow(new NotFoundException("user", uuid.toString()));

        assertThatThrownBy(() -> sut.execute(invalidId, updatedUser))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Resource not found");
    }

    @Test
    @Description("Should throw an IllegalArgumentException when invalid UUID is provided")
    void shouldThrowAnIllegalArgumentExceptionWhenInValidUuidIsProvided() {
        String invalidUuid = "123L";

        assertThatThrownBy(() -> sut.execute(invalidUuid, updatedUser))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid UUID string: " + invalidUuid);
    }

    @Test
    @Description("Should throw an IllegalArgumentException when request is null")
    void shouldThrowAnIllegalArgumentExceptionWhenRequestIsNull() {
        String id = uuid.toString();

        assertThatThrownBy(() -> sut.execute(id, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Missing argument of type UpdateUserRequest");
    }

}
