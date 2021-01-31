package com.gustavoaos.singledigit.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gustavoaos.singledigit.application.*;
import com.gustavoaos.singledigit.application.request.ComputeSingleDigitRequest;
import com.gustavoaos.singledigit.application.response.UserResponse;
import com.gustavoaos.singledigit.domain.SingleDigit;
import com.gustavoaos.singledigit.domain.exception.NotFoundException;
import com.gustavoaos.singledigit.domain.exception.ArgumentOutOfRangeException;
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

    @MockBean
    private ListSingleDigitsInteractor listSingleDigitsInteractor;

    private String mockUserUUID;
    private UserResponse mockUser;
    private SingleDigit mockSd;

    @BeforeEach
    void initEach() {
        mockSd = new SingleDigit("9875", "4");
        mockUserUUID = UUID.randomUUID().toString();
        mockUser = UserResponse
                .builder()
                .id(mockUserUUID)
                .name("Joe Doe")
                .email("joedoe@mail.com")
                .singleDigits(Collections.singletonList(mockSd))
                .build();
    }

    @Test
    @Description("Should return 200 http status and single digit when valid n and k are provided")
    void shouldReturn200AndSingleDigitWhenValidNAndKAreProvided() throws Exception {
        ComputeSingleDigitRequest request = ComputeSingleDigitRequest.builder().n("9875").k("4").build();
        Integer expected = 8;

        Mockito.when(computeSingleDigitInteractor.execute(
                Mockito.any()
        )).thenReturn(expected);

        MvcResult res = mockMvc.perform(get("/users/compute")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn();
        String resContent = res.getResponse().getContentAsString();

        Mockito.verify(computeSingleDigitInteractor, Mockito.times(1)).execute(Mockito.any());
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
        )).thenThrow(new ArgumentOutOfRangeException("n", "1", "10^100000"));

        mockMvc.perform(get("/users/compute")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest());

        Mockito.verify(computeSingleDigitInteractor, Mockito.times(1)).execute(Mockito.any());
    }

    @Test
    @Description("Should return 400 http status when k is out of bound limits")
    void shouldReturn400WhenKIsOutOfBoundLimits() throws Exception {
        ComputeSingleDigitRequest request = ComputeSingleDigitRequest.builder().n("9875").k("-4").build();

        Mockito.when(computeSingleDigitInteractor.execute(
                Mockito.any()
        )).thenThrow(new ArgumentOutOfRangeException("k", "1", "10^5"));

        mockMvc.perform(get("/users/compute")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest());

        Mockito.verify(computeSingleDigitInteractor, Mockito.times(1)).execute(Mockito.any());
    }

    @Test
    @Description("Should return 400 http when invalid id is provided")
    void shouldReturn400WhenInValidIdIsProvided() throws Exception {
        ComputeSingleDigitRequest request = ComputeSingleDigitRequest.builder().n("9875").k("4").build();
        Integer expected = 8;

        Mockito.when(computeSingleDigitInteractor.execute(
                Mockito.any(), Mockito.anyString()
        )).thenThrow(new NotFoundException("user", mockUserUUID));

        mockMvc.perform(get("/users/compute?id=" + mockUserUUID)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest());

        Mockito.verify(computeSingleDigitInteractor, Mockito.times(1))
                .execute(Mockito.any(), Mockito.anyString());
        Mockito.verify(computeSingleDigitInteractor, Mockito.times(0))
                .execute(Mockito.any());
    }

    @Test
    @Description("Should return 200 http status and computes single digit when valid n, k and id are provided")
    void shouldReturn200AndComputesSingleDigitWhenValidParametersAreProvided() throws Exception {
        ComputeSingleDigitRequest request = ComputeSingleDigitRequest.builder().n("9875").k("4").build();
        Integer expected = 8;

        Mockito.when(computeSingleDigitInteractor.execute(
                Mockito.any(), Mockito.anyString()
        )).thenReturn(expected);

        MvcResult res = mockMvc.perform(get("/users/compute?id=" + mockUserUUID)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn();
        String resContent = res.getResponse().getContentAsString();

        Mockito.verify(computeSingleDigitInteractor, Mockito.times(1))
                .execute(Mockito.any(), Mockito.anyString());
        Mockito.verify(computeSingleDigitInteractor, Mockito.times(0))
                .execute(Mockito.any());

        assertThat(Integer.parseInt(resContent)).isEqualTo(expected);
    }

}
