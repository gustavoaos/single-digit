package com.gustavoaos.singledigit.application.request;

import com.gustavoaos.singledigit.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class CreateUserRequest {

    private String name;
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
