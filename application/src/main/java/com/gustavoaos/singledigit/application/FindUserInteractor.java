package com.gustavoaos.singledigit.application;

import com.gustavoaos.singledigit.application.response.UserResponse;

public interface FindUserInteractor {

    UserResponse execute(String id);

    UserResponse execute(String id, String publicKey);
}
