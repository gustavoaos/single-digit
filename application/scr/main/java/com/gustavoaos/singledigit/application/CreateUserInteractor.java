package com.gustavoaos.singledigit.application;

import com.gustavoaos.singledigit.application.request.CreateUserRequest;
import com.gustavoaos.singledigit.application.response.UserResponse;

public interface CreateUserInteractor {

    UserResponse execute(CreateUserRequest request);
}
