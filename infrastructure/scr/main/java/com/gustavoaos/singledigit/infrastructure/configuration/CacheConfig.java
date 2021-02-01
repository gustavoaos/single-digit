package com.gustavoaos.singledigit.infrastructure.configuration;

import com.gustavoaos.singledigit.infrastructure.cache.LRUCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig {

    @Bean
    public <K, V> LRUCache<K, V> getCache(@Value("${app.cache-capacity}") Long cacheCapacity) {
        return new LRUCache<>(10);
    }

}
