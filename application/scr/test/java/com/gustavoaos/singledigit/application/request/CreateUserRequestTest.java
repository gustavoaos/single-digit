package com.gustavoaos.singledigit.application.request;

import com.gustavoaos.singledigit.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class CreateUserRequestTest {

    CreateUserRequest sut;
    User domainUser;

    @BeforeEach
    void initEach() {
        String name = "Joe Doe";
        String email = "joe@doe.com";

        sut = CreateUserRequest
                .builder().name(name).email(email).build();

        domainUser = User.builder()
                .name(name).email(email).build();
    }

    @Test
    @Description("Should return an User when is pass a CreateUserRequest")
    void shouldMapCreateUserRequestToUserDomain() {
        assertThat(sut.toDomain().getUuid()).isNotNull();
        assertThat(sut.toDomain().getName()).isEqualTo(domainUser.getName());
        assertThat(sut.toDomain().getEmail()).isEqualTo(domainUser.getEmail());
    }

}
