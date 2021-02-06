package com.gustavoaos.singledigit.application.impl;

import com.gustavoaos.singledigit.application.DeleteUserInteractor;
import com.gustavoaos.singledigit.application.response.UserResponse;
import com.gustavoaos.singledigit.domain.User;
import com.gustavoaos.singledigit.domain.exception.NotFoundException;
import com.gustavoaos.singledigit.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DeleteUserInteractorImpl implements DeleteUserInteractor {

    private final UserRepository userRepository;

    @Autowired
    public DeleteUserInteractorImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void execute(String id) {
        User user = userRepository
                .findById(UUID.fromString(id))
                .orElseThrow(() -> new NotFoundException("user", id));

        userRepository.delete(user);
    }
}
