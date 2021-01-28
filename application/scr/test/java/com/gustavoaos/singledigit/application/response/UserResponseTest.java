package com.gustavoaos.singledigit.application.response;

import com.gustavoaos.singledigit.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class UserResponseTest {

    UserResponse sut;
    User domainUser;

    @BeforeEach
    void initEach() {
        String name = "Joe Doe";
        String email = "joe@doe.com";
        UUID uuid = UUID.randomUUID();

        sut = UserResponse
                .builder().id(uuid.toString()).name(name).email(email).build();

        domainUser = User
                .builder().uuid(uuid).name(name).email(email).build();
    }

    @Test
    @Description("Should return an User when is pass a CreateUserRequest")
    void shouldMapCreateUserRequestToUserDomain() {
        assertThat(UserResponse.from(domainUser)).isEqualTo(sut);
    }
}
