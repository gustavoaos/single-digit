package com.gustavoaos.singledigit.application.response;

import com.gustavoaos.singledigit.domain.SingleDigit;
import com.gustavoaos.singledigit.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class UserResponse {

    private String id;
    private String name;
    private String email;
    private String publicKey;

    @Builder.Default
    private List<SingleDigit> singleDigits = new ArrayList<>();

    public static UserResponse from(User user) {
        if (user == null || user.getUuid() == null || user.getName().isEmpty() || user.getEmail().isEmpty()) {
            throw new InvalidParameterException("Invalid user provided");
        }

        return UserResponse.builder()
                .id(user.getUuid().toString())
                .name(user.getName())
                .email(user.getEmail())
                .publicKey(user.getPublicKey())
                .singleDigits(user.getSingleDigits())
                .build();
    }

}
