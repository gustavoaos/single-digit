package com.gustavoaos.singledigit.application;

import com.gustavoaos.singledigit.application.request.UpdateUserRequest;
import com.gustavoaos.singledigit.application.response.UserResponse;

public interface UpdateUserInteractor {

    UserResponse execute(String id, UpdateUserRequest request);
}
