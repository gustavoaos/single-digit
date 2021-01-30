package com.gustavoaos.singledigit.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gustavoaos.singledigit.application.*;
import com.gustavoaos.singledigit.application.request.ComputeSingleDigitRequest;
import com.gustavoaos.singledigit.application.request.UpdateUserRequest;
import com.gustavoaos.singledigit.application.response.UserResponse;
import com.gustavoaos.singledigit.domain.exception.ParameterOutOfRangeException;
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

import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
class UserControllerComputeSingleDigitUseCaseTest {

    @Autowired
    private ObjectMapper objectMapper;

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

    private String mockUserUUID;
    private UserResponse mockUser;
    private UserResponse updatedUser;
    private UpdateUserRequest request;

    @BeforeEach
    void initEach() {
        mockUserUUID = UUID.randomUUID().toString();
        mockUser = UserResponse
                .builder()
                .id(mockUserUUID)
                .name("Valid Name")
                .email("valid@mail.com")
                .singleDigits(Collections.emptyList())
                .build();
        updatedUser = UserResponse
                .builder()
                .id(mockUserUUID)
                .name("Updated name")
                .email("updated@mail.com")
                .singleDigits(Collections.emptyList())
                .build();
        request = UpdateUserRequest
                .builder()
                .name("Updated name")
                .email("updated@mail.com")
                .build();
    }

    @Test
    @Description("Should return 200 http status and single digit when valid n and k are provided")
    void shouldReturn200AndSingleDigitWhenValidNAndKAreProvided() throws Exception {
        Integer expected = 8;
        Mockito.when(computeSingleDigitInteractor.execute(
                Mockito.any()
        )).thenReturn(expected);

        ComputeSingleDigitRequest request = ComputeSingleDigitRequest.builder().n("9875").k("4").build();
        MvcResult res = mockMvc.perform(get("/users/compute")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn();
        String resContent = res.getResponse().getContentAsString();

        assertThat(Integer.parseInt(resContent)).isEqualTo(expected);
    }

    @Test
    @Description("Should return 400 http status when n is not provided")
    void shouldReturn400WhenNIsNotProvided() throws Exception {
        ComputeSingleDigitRequest request = ComputeSingleDigitRequest.builder().k("4").build();

        mockMvc.perform(get("/users/compute")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @Description("Should return 400 http status when k is not provided")
    void shouldReturn400WhenKIsNotProvided() throws Exception {
        ComputeSingleDigitRequest request = ComputeSingleDigitRequest.builder().n("9875").build();

        mockMvc.perform(get("/users/compute")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @Description("Should return 400 http status when n is out of bound limits")
    void shouldReturn400WhenNIsOutOfBoundLimits() throws Exception {
        ComputeSingleDigitRequest request = ComputeSingleDigitRequest.builder().n("-1").k("4").build();

        Mockito.when(computeSingleDigitInteractor.execute(
                Mockito.any()
        )).thenThrow(new ParameterOutOfRangeException("n", "1", "10^100000"));

        mockMvc.perform(get("/users/compute")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @Description("Should return 400 http status when k is out of bound limits")
    void shouldReturn400WhenKIsOutOfBoundLimits() throws Exception {
        ComputeSingleDigitRequest request = ComputeSingleDigitRequest.builder().n("9875").k("-4").build();

        Mockito.when(computeSingleDigitInteractor.execute(
                Mockito.any()
        )).thenThrow(new ParameterOutOfRangeException("k", "1", "10^5"));

        mockMvc.perform(get("/users/compute")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest());
    }

}
