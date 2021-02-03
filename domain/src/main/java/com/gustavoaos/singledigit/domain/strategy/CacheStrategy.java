package com.gustavoaos.singledigit.domain.strategy;

import com.gustavoaos.singledigit.application.request.ComputeSingleDigitRequest;
import com.gustavoaos.singledigit.domain.exception.NotFoundException;

import java.util.Map;

public class CacheStrategy implements SingleDigitStrategy {

    private final Map<ComputeSingleDigitRequest, Integer> cache;

    public CacheStrategy(Map<ComputeSingleDigitRequest, Integer> cache) {
        this.cache = cache;
    }

    @Override
    public int compute(String n, String k) {
        ComputeSingleDigitRequest key = ComputeSingleDigitRequest.builder().n(n).k(k).build();

        if (!this.cache.containsKey(key)) {
            throw new NotFoundException("cache", key.toString());
        }

        return this.cache.get(key);
    }
}
