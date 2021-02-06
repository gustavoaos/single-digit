package com.gustavoaos.singledigit.application.impl;

import com.gustavoaos.singledigit.application.ComputeSingleDigitInteractor;
import com.gustavoaos.singledigit.application.request.ComputeSingleDigitRequest;
import com.gustavoaos.singledigit.domain.SingleDigit;
import com.gustavoaos.singledigit.domain.User;
import com.gustavoaos.singledigit.domain.exception.NotFoundException;
import com.gustavoaos.singledigit.domain.repository.CacheRepository;
import com.gustavoaos.singledigit.domain.repository.UserRepository;
import com.gustavoaos.singledigit.domain.strategy.ComputeStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.UUID;

@Component
public class ComputeSingleDigitInteractorImpl implements ComputeSingleDigitInteractor {

    private final UserRepository userRepository;
    private final CacheRepository cacheRepository;

    @Autowired
    public ComputeSingleDigitInteractorImpl(
            UserRepository userRepository,
            CacheRepository cacheRepository) {
        this.userRepository = userRepository;
        this.cacheRepository = cacheRepository;
    }

    @Override
    public Integer execute(@Valid ComputeSingleDigitRequest request) {
        SingleDigit sd = this.getFromCache(request);
        return sd.getResult();
    }

    @Override
    public Integer execute(@Valid ComputeSingleDigitRequest request, String id) {
        User user = userRepository
                .findById(UUID.fromString(id))
                .orElseThrow(() -> new NotFoundException("user", id));

        SingleDigit sd = this.getFromCache(request);

        user.getSingleDigits().add(sd);
        userRepository.save(user);

        return sd.getResult();
    }

    private SingleDigit getFromCache(@Valid ComputeSingleDigitRequest key) {
        return this.cacheRepository
                .getFromCache(key)
                .orElseGet(() -> createAndPutOnCache(key));
    }

    private SingleDigit createAndPutOnCache(@Valid ComputeSingleDigitRequest key) {
        ComputeStrategy computeStr = new ComputeStrategy();
        SingleDigit sd = new SingleDigit(key.getN(), key.getK(), computeStr);

        this.cacheRepository.putOnCache(key, sd.getResult());

        return sd;
    }

}
