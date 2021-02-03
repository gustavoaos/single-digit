package com.gustavoaos.singledigit.domain.repository;

import com.gustavoaos.singledigit.application.request.ComputeSingleDigitRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

import java.util.Map;

@SpringBootTest
class CacheRepositoryTest {

    @Mock
    private Map<ComputeSingleDigitRequest, Integer> cache;

    @InjectMocks
    private CacheRepository sut;

    private ComputeSingleDigitRequest request;

    @Test
    @Description("Should return empty optional when value is not stored in cache")
    void shouldReturnEmptyOptionalWhenValueIsNotStoredInCache() {
        request = ComputeSingleDigitRequest.builder().n("9875").k("4").build();

        when(cache.containsKey(request)).thenReturn(false);

        assertThat(sut.getFromCache(request)).isEmpty();
    }
}
