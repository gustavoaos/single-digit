package com.gustavoaos.singledigit.domain.repository;

import com.gustavoaos.singledigit.application.request.ComputeSingleDigitRequest;
import com.gustavoaos.singledigit.domain.SingleDigit;
import com.gustavoaos.singledigit.domain.strategy.CacheStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
public class CacheRepository {

    private final Map<ComputeSingleDigitRequest, Integer> cache;

    @Autowired
    public CacheRepository(Map<ComputeSingleDigitRequest, Integer> cache) {
        this.cache = cache;
    }

    public Optional<SingleDigit> getFromCache(ComputeSingleDigitRequest key) {
        if (cache.containsKey(key)) {
            CacheStrategy cacheStr = new CacheStrategy(cache);
            SingleDigit sd = new SingleDigit(key.getN(), key.getK(), cacheStr);

            return Optional.of(sd);
        }

        return Optional.empty();
    }

    public void putOnCache(ComputeSingleDigitRequest key, Integer value) {
        cache.put(key, value);
    }

}
