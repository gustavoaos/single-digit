package com.gustavoaos.singledigit.application.impl;

import com.gustavoaos.singledigit.application.FindUserInteractor;
import com.gustavoaos.singledigit.application.response.UserResponse;
import com.gustavoaos.singledigit.domain.User;
import com.gustavoaos.singledigit.domain.exception.NotFoundException;
import com.gustavoaos.singledigit.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FindUserInteractorImpl implements FindUserInteractor {

    private final UserRepository userRepository;

    @Autowired
    public FindUserInteractorImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponse execute(String id) {
        User user = userRepository
                .findById(UUID.fromString(id))
                .orElseThrow(() -> new NotFoundException("user", id));

        return UserResponse.from(user);
    }
}
