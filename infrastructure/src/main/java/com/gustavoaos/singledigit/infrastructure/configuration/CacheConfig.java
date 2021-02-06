package com.gustavoaos.singledigit.infrastructure.configuration;

import com.gustavoaos.singledigit.infrastructure.cache.LRUCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig {

    private static final int CACHE_ENTRIES = 10;
    @Bean
    public <K, V> LRUCache<K, V> getCache() {
        return new LRUCache<>(CACHE_ENTRIES);
    }

}
