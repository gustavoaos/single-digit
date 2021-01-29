package com.gustavoaos.singledigit.application.impl;

import com.gustavoaos.singledigit.application.CreateUserInteractor;
import com.gustavoaos.singledigit.application.request.CreateUserRequest;
import com.gustavoaos.singledigit.application.response.UserResponse;
import com.gustavoaos.singledigit.domain.User;
import com.gustavoaos.singledigit.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class CreateUserInteractorImpl implements CreateUserInteractor {

    private final UserRepository userRepository;

    @Autowired
    public CreateUserInteractorImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponse execute(CreateUserRequest request) {
        User user = request.toDomain();
        User domainUser = this.userRepository.save(user);
        return UserResponse.from(domainUser);
    }
}
