package com.gustavoaos.singledigit.application.response;

import com.gustavoaos.singledigit.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserResponse {

    private String id;
    private String name;
    private String email;

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .id(user.getUuid().toString())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}
