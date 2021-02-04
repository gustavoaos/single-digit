package com.gustavoaos.singledigit.application;

import com.gustavoaos.singledigit.application.request.CreateUserRequest;
import com.gustavoaos.singledigit.application.response.UserResponse;
import com.gustavoaos.singledigit.domain.exception.CryptoException;

public interface CreateUserInteractor {

    UserResponse execute(CreateUserRequest request);
}
