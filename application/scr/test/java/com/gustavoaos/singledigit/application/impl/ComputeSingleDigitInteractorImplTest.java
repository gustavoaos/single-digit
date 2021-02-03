package com.gustavoaos.singledigit.application.impl;

import com.gustavoaos.singledigit.application.request.ComputeSingleDigitRequest;
import com.gustavoaos.singledigit.domain.SingleDigit;
import com.gustavoaos.singledigit.domain.User;
import com.gustavoaos.singledigit.domain.repository.CacheRepository;
import com.gustavoaos.singledigit.domain.repository.UserRepository;
import com.gustavoaos.singledigit.domain.strategy.CacheStrategy;
import com.gustavoaos.singledigit.domain.strategy.ComputeStrategy;
import com.gustavoaos.singledigit.domain.strategy.SingleDigitStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@SpringBootTest
class ComputeSingleDigitInteractorImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CacheRepository cacheRepository;

    @InjectMocks
    private ComputeSingleDigitInteractorImpl sut;

    private UUID uuid;

    @BeforeEach
    void setMockOutput() {
        SingleDigitStrategy strategy = new ComputeStrategy();
        SingleDigit sd = new SingleDigit("9875", "4", strategy);
        uuid = UUID.randomUUID();
        String name = "Joe Doe";
        String email = "joe@doe.com";

        User user = User.builder()
                .uuid(uuid).name(name).email(email).build();
        User updatedUser = User.builder()
                .uuid(uuid).name(name).email(email).singleDigits(Collections.singletonList(sd)).build();

        when(userRepository.findById(uuid)).thenReturn(Optional.of(user));
        when(userRepository.save(any())).thenReturn(updatedUser);
    }

    @Test
    @Description("Should throw an IllegalArgumentException when request is null")
    void shouldThrowAnIllegalArgumentExceptionWhenRequestIsNull() {
        assertThatThrownBy(() -> sut.execute(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Missing argument of type ComputeSingleDigitRequest");
    }

    @Test
    @Description("Should return compute single digit when valid n and k are provided")
    void shouldComputeSingleDigitWhenValidNAndKAreProvided() {
        ComputeSingleDigitRequest request = ComputeSingleDigitRequest.builder().n("9875").k("4").build();
        Integer expected = 8;

        assertThat(sut.execute(request)).isEqualTo(expected);
    }

    @Test
    @Description("Should return compute single digit when valid n, k and id are provided")
    void shouldComputeSingleDigitWhenValidArgumentsAreProvided() {
        ComputeSingleDigitRequest request = ComputeSingleDigitRequest.builder().n("9875").k("4").build();
        Integer expected = 8;

        assertThat(sut.execute(request, uuid.toString())).isEqualTo(expected);

        verify(userRepository, times(1)).findById(uuid);
        verify(userRepository, times(1)).save(any());
    }

}
