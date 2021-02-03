package com.gustavoaos.singledigit.infrastructure.cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;

import java.util.LinkedHashMap;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class LRUCacheTest {

    private LinkedHashMap<String, Integer> sut;

    @BeforeEach
    void initEach() {
        int MAX_ENTRIES = 10;
        sut = new LRUCache<>(MAX_ENTRIES);
    }

    @Test
    @Description("Should stores no more than max capacity defined")
    void shouldStoresNoMoreThanMaxCapacityDefined() {
        sut.put("1", 1);
        sut.put("2", 2);
        sut.put("3", 3);
        sut.put("4", 4);
        sut.put("5", 5);
        sut.put("6", 6);
        sut.put("7", 7);
        sut.put("8", 8);
        sut.put("9", 9);
        sut.put("10", 10);
        sut.put("11", 11);

        assertThat(sut.containsKey("1")).isFalse();

        assertThat(sut.containsKey("2")).isTrue();
        assertThat(sut.containsKey("5")).isTrue();
        assertThat(sut.containsKey("11")).isTrue();

        assertThat(sut.size()).isEqualTo(10);
    }
}
