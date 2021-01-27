package com.gustavoaos.singledigit.application.impl;

import com.gustavoaos.singledigit.application.CreateUserInteractor;
import com.gustavoaos.singledigit.application.request.CreateUserRequest;
import com.gustavoaos.singledigit.application.response.UserResponse;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CreateUserInteractorImpl implements CreateUserInteractor {

    @Override
    public UserResponse execute(CreateUserRequest request) {
        return null;
    }
}
