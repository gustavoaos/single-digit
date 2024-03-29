package com.gustavoaos.singledigit.api.controller;

import com.gustavoaos.singledigit.application.*;
import com.gustavoaos.singledigit.application.response.SingleDigitListResponse;
import com.gustavoaos.singledigit.domain.SingleDigit;
import com.gustavoaos.singledigit.domain.exception.NotFoundException;
import com.gustavoaos.singledigit.domain.strategy.ComputeStrategy;
import com.gustavoaos.singledigit.domain.strategy.SingleDigitStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Description;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Arrays;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
class UserControllerListSingleDigitsUseCaseTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateUserInteractor createUserInteractor;

    @MockBean
    private FindUserInteractor findUserInteractor;

    @MockBean
    private DeleteUserInteractor deleteUserInteractor;

    @MockBean
    private UpdateUserInteractor updateUserInteractor;

    @MockBean
    private ComputeSingleDigitInteractor computeSingleDigitInteractor;

    @MockBean
    private ListSingleDigitsInteractor listSingleDigitsInteractor;

    private String mockUserUUID;
    private SingleDigitListResponse mockList;
    private final String BASE_REQUEST_MAPPING = "/single-digit/api/v1/users";

    @BeforeEach
    void initEach() {
        SingleDigitStrategy strategy = new ComputeStrategy();
        mockUserUUID = UUID.randomUUID().toString();
        mockList = SingleDigitListResponse
                .builder()
                .singleDigits(Arrays.asList(
                        new SingleDigit("9875", "4", strategy),
                        new SingleDigit("123", "2", strategy)
                ))
                .build();
    }

    @Test
    @Description("Should return 404 http status when user not found")
    void shouldReturn404WhenUserNotFound() throws Exception {
        Mockito.when(listSingleDigitsInteractor.execute(
                mockUserUUID)
        ).thenThrow(new NotFoundException("resource", mockUserUUID));

        MvcResult result = mockMvc.perform(get(BASE_REQUEST_MAPPING + "/" + mockUserUUID + "/list")
                .contentType("application/json"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).contains("Resource not found", mockUserUUID);
    }

    @Test
    @Description("Should return list with previous single digits computed for user when valid id is provided")
    void shouldReturnListWithPreviousSingleDigitsComputedForUserWhenValidIdIsProvided() throws Exception {
        Mockito.when(listSingleDigitsInteractor.execute(
                mockUserUUID)
        ).thenReturn(mockList);

        mockMvc.perform(get(BASE_REQUEST_MAPPING + "/" + mockUserUUID + "/list")
                .contentType("application/json"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.singleDigits", hasSize(2)));
    }

}
