package com.gustavoaos.singledigit.application.request;

import com.gustavoaos.singledigit.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class CreateUserRequest {

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Invalid email provided")
    private String email;

    public User toDomain() {
        return User.builder()
                .uuid(UUID.randomUUID())
                .name(this.name)
                .email(this.email)
                .build();
    }

    public User toDomain(String publicKey, String privateKey) {
        return User.builder()
                .uuid(UUID.randomUUID())
                .name(this.name)
                .email(this.email)
                .publicKey(publicKey)
                .privateKey(privateKey)
                .build();
    }

}
