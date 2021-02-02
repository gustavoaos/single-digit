package com.gustavoaos.singledigit.application.impl;

import com.gustavoaos.singledigit.application.ComputeSingleDigitInteractor;
import com.gustavoaos.singledigit.application.request.ComputeSingleDigitRequest;
import com.gustavoaos.singledigit.domain.SingleDigit;
import com.gustavoaos.singledigit.domain.User;
import com.gustavoaos.singledigit.domain.exception.NotFoundException;
import com.gustavoaos.singledigit.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class ComputeSingleDigitInteractorImpl implements ComputeSingleDigitInteractor {

    private final UserRepository userRepository;
    private final Map<ComputeSingleDigitRequest, Integer> cache;

    @Autowired
    public ComputeSingleDigitInteractorImpl(
            UserRepository userRepository,
            Map<ComputeSingleDigitRequest, Integer> cache) {
        this.userRepository = userRepository;
        this.cache = cache;
    }

    @Override
    public Integer execute(ComputeSingleDigitRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Missing argument of type ComputeSingleDigitRequest");
        }

        SingleDigit sd = this.get(request);
        return sd.getResult();
    }

    @Override
    public Integer execute(ComputeSingleDigitRequest request, String id) {
        if (request == null) {
            throw new IllegalArgumentException("Missing argument of type ComputeSingleDigitRequest");
        }

        User user = userRepository
                .findById(UUID.fromString(id))
                .orElseThrow(() -> new NotFoundException("user", id));

        SingleDigit sd = this.get(request);

        user.getSingleDigits().add(sd);
        userRepository.save(user);

        return sd.getResult();
    }

    private SingleDigit get(ComputeSingleDigitRequest request) {
        if (cache.containsKey(request)) {
            return request.toDomain(cache.get(request));
        }

        SingleDigit sd = request.toDomain();
        cache.put(request, sd.getResult());

        return sd;
    }
}
