package com.gustavoaos.singledigit.application.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class UserResponse {

    private UUID id;
    private String name;
    private String email;
}
