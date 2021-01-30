package com.gustavoaos.singledigit.application.impl;

import com.gustavoaos.singledigit.application.UpdateUserInteractor;
import com.gustavoaos.singledigit.application.request.UpdateUserRequest;
import com.gustavoaos.singledigit.application.response.UserResponse;
import com.gustavoaos.singledigit.domain.User;
import com.gustavoaos.singledigit.domain.exception.NotFoundException;
import com.gustavoaos.singledigit.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UpdateUserInteractorImpl implements UpdateUserInteractor {

    private final UserRepository userRepository;

    @Autowired
    public UpdateUserInteractorImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponse execute(String id, UpdateUserRequest request) {
        User user = userRepository
                .findById(UUID.fromString(id))
                .orElseThrow(() -> new NotFoundException("user", id));

        if (request == null) {
            throw new IllegalArgumentException("Missing argument of type UpdateUserRequest");
        }
        if (request.getName() != null) {
            user.setName(request.getName());
        }
        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }

        userRepository.save(user);

        return UserResponse.from(user);
    }
}
