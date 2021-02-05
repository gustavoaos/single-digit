package com.gustavoaos.singledigit.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gustavoaos.singledigit.application.*;
import com.gustavoaos.singledigit.application.response.UserResponse;
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
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
class UserControllerFindUserUseCaseTest {

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
    private UserResponse mockUser;
    private SingleDigitStrategy strategy;

    @BeforeEach
    void initEach() {
        strategy = new ComputeStrategy();
        mockUserUUID = UUID.randomUUID().toString();
        mockUser = UserResponse
                .builder()
                .id(mockUserUUID)
                .name("Valid Name")
                .email("valid@mail.com")
                .singleDigits(Collections.emptyList())
                .build();
    }

    @Test
    @Description("Should return 200 http status and user when valid id is provided")
    void shouldReturn200AndUserWhenValidIdIsProvided() throws Exception {
        Mockito.when(findUserInteractor.execute(mockUserUUID)).thenReturn(mockUser);

        mockMvc.perform(get("/users/" + mockUserUUID)
                .contentType("application/json"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(mockUser.getId().toString())))
                .andExpect(jsonPath("$.name", is(mockUser.getName())))
                .andExpect(jsonPath("$.email", is(mockUser.getEmail())))
                .andExpect(jsonPath("$.singleDigits", hasSize(0)));
    }

    @Test
    @Description("Should return 200 http status and user with single digits previous calculated when valid id is provided")
    void shouldReturn200AndUserWithSingleDigitsPreviousCalculatedWhenValidIdIsProvided() throws Exception {
        List<SingleDigit> sd = Arrays.asList(
                new SingleDigit("1", "1", strategy),
                new SingleDigit("7894", "4", strategy));
        mockUser.setSingleDigits(sd);
        Mockito.when(findUserInteractor.execute(mockUserUUID)).thenReturn(mockUser);

        mockMvc.perform(get("/users/" + mockUserUUID)
                .contentType("application/json"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(mockUser.getId().toString())))
                .andExpect(jsonPath("$.name", is(mockUser.getName())))
                .andExpect(jsonPath("$.email", is(mockUser.getEmail())))
                .andExpect(jsonPath("$.singleDigits", hasSize(2)));
    }

    @Test
    @Description("Should return 404 http status when user not found")
    void shouldReturn404WhenUserNotFound() throws Exception {
        Mockito.when(findUserInteractor.execute(mockUserUUID))
                .thenThrow(new NotFoundException("resource", mockUserUUID));

        MvcResult result = mockMvc.perform(get("/users/" + mockUserUUID)
                .contentType("application/json"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).contains("Resource not found", mockUserUUID);
    }

}