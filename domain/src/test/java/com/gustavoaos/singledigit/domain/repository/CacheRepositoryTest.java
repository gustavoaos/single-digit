package com.gustavoaos.singledigit.domain.repository;

import com.gustavoaos.singledigit.application.request.ComputeSingleDigitRequest;
import com.gustavoaos.singledigit.domain.SingleDigit;
import com.gustavoaos.singledigit.domain.strategy.ComputeStrategy;
import com.gustavoaos.singledigit.domain.strategy.SingleDigitStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.Optional;

@SpringBootTest
class CacheRepositoryTest {

    @Mock
    private Map<ComputeSingleDigitRequest, Integer> cache;

    @InjectMocks
    private CacheRepository sut;

    private ComputeSingleDigitRequest request;
    private SingleDigit sd;

    @BeforeEach
    void initEach() {
        SingleDigitStrategy strategy = new ComputeStrategy();
        String n = "9875";
        String k = "4";

        request = ComputeSingleDigitRequest.builder().n(n).k(k).build();
        sd = new SingleDigit(n, k, strategy);
    }

    @Test
    @Description("Should return empty optional when value is not stored in cache")
    void shouldReturnEmptyOptionalWhenValueIsNotStoredInCache() {
        when(cache.containsKey(request)).thenReturn(false);

        assertThat(sut.getFromCache(request)).isEmpty();
    }

    @Test
    @Description("Should return single digit optional when value is stored in cache")
    void shouldReturnSingleDigitOptionalWhenValueIsStoredInCache() {
        when(cache.containsKey(request)).thenReturn(true);
        when(cache.get(request)).thenReturn(sd.getResult());

        Optional<SingleDigit> response = sut.getFromCache(request);

        assertThat(response).isPresent();
        assertThat(response.get().getN()).isEqualTo(sd.getN());
        assertThat(response.get().getK()).isEqualTo(sd.getK());
        assertThat(response.get().getResult()).isEqualTo(sd.getResult());
    }

}
