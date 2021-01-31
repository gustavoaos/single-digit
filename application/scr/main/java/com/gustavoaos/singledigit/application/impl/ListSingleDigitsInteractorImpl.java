package com.gustavoaos.singledigit.application.impl;

import com.gustavoaos.singledigit.application.ListSingleDigitsInteractor;
import com.gustavoaos.singledigit.application.response.SingleDigitListResponse;
import com.gustavoaos.singledigit.domain.User;
import com.gustavoaos.singledigit.domain.exception.NotFoundException;
import com.gustavoaos.singledigit.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ListSingleDigitsInteractorImpl implements ListSingleDigitsInteractor {

    private final UserRepository userRepository;

    @Autowired
    public ListSingleDigitsInteractorImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public SingleDigitListResponse execute(String id) {
        User user = userRepository
                .findById(UUID.fromString(id))
                .orElseThrow(() -> new NotFoundException("user", id));

        return SingleDigitListResponse.from(user);
    }

}
