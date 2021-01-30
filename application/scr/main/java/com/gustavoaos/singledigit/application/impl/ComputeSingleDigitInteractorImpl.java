package com.gustavoaos.singledigit.application.impl;

import com.gustavoaos.singledigit.application.ComputeSingleDigitInteractor;
import com.gustavoaos.singledigit.application.request.ComputeSingleDigitRequest;
import com.gustavoaos.singledigit.domain.SingleDigit;
import com.gustavoaos.singledigit.domain.exception.NotFoundException;
import com.gustavoaos.singledigit.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ComputeSingleDigitInteractorImpl implements ComputeSingleDigitInteractor {

    private final UserRepository userRepository;

    @Autowired
    public ComputeSingleDigitInteractorImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Integer execute(ComputeSingleDigitRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Missing argument of type ComputeSingleDigitRequest");
        }

        SingleDigit sd = request.toDomain();
        return sd.compute();
    }

    @Override
    public Integer execute(ComputeSingleDigitRequest request, String id) {
        return null;
    }
}
