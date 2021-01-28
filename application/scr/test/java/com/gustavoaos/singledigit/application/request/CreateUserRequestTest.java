package com.gustavoaos.singledigit.application.request;

import com.gustavoaos.singledigit.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;

import static org.assertj.core.api.AssertionsForClassTypes.*;

@SpringBootTest
class CreateUserRequestTest {

    CreateUserRequest createUserRequest;
    User domainUser;

    @BeforeEach
    void initEach() {
        String name = "Joe Doe";
        String email = "joe@doe.com";

        createUserRequest = CreateUserRequest
                .builder().name(name).email(email).build();

        domainUser = User.builder()
                .name(name).email(email).build();
    }

    @Test
    @Description("Should return an User when is pass a CreateUserRequest")
    void shouldMapCreateUserRequestToUserDomain() {
        assertThat(createUserRequest.toDomain()).isEqualTo(domainUser);
    }

}
