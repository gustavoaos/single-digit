package com.gustavoaos.singledigit.application.impl;

import com.gustavoaos.singledigit.application.response.SingleDigitListResponse;
import com.gustavoaos.singledigit.application.response.UserResponse;
import com.gustavoaos.singledigit.domain.SingleDigit;
import com.gustavoaos.singledigit.domain.User;
import com.gustavoaos.singledigit.domain.exception.NotFoundException;
import com.gustavoaos.singledigit.domain.repository.UserRepository;
import com.gustavoaos.singledigit.domain.strategy.SingleDigitStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@SpringBootTest
class ListSingleDigitsInteractorImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ListSingleDigitsInteractorImpl sut;

    private UUID uuid;
    private String name;
    private String email;
    private List<SingleDigit> mockList;
    private SingleDigitStrategy strategy;

    @BeforeEach
    void initEach() {
        uuid = UUID.randomUUID();
        name = "Joe Doe";
        email = "joe@doe.com";
        mockList = Arrays.asList(
                new SingleDigit("9875", "4", strategy),
                new SingleDigit("123", "2", strategy));
        User user = User
                .builder()
                .uuid(uuid)
                .name(name)
                .email(email)
                .singleDigits(mockList)
                .build();

        when(userRepository.findById(uuid)).thenReturn(Optional.of(user));
    }

    @Test
    @Description("Should return single digit list when valid id is provided")
    void shouldReturnSingleDigitListResponseWhenValidIdIsProvided() {
        SingleDigitListResponse list = SingleDigitListResponse.builder()
                .singleDigits(mockList).build();

        assertThat(sut.execute(uuid.toString())).isEqualTo(list);
        assertThat(sut.execute(uuid.toString()).getSingleDigits().size()).isEqualTo(2);
    }

    @Test
    @Description("Should throw a NotFoundException when invalid id is provided")
    void shouldThrowANotFoundExceptionWhenInValidIdIsProvided() {
        String invalidId = uuid.toString();

        when(userRepository.findById(uuid)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> sut.execute(invalidId))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Resource not found");
    }

}
