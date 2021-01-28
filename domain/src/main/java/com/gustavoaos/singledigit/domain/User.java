package com.gustavoaos.singledigit.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class User {

    private UUID uuid;
    private String name;
    private String email;
}
