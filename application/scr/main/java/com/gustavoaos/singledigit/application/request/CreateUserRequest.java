package com.gustavoaos.singledigit.application.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CreateUserRequest {

    private String name;
    private String email;

}
