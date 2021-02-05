package com.gustavoaos.singledigit.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gustavoaos.singledigit.application.*;
import com.gustavoaos.singledigit.application.response.UserResponse;
import com.gustavoaos.singledigit.domain.error.ApiError;
import com.gustavoaos.singledigit.domain.exception.NotFoundException;
import org.assertj.core.api.AssertionsForClassTypes;
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
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
class UserControllerDeleteUserUseCaseTest {

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
    }

    @Test
    @Description("Should return 204 http status when delete existing user")
    void shouldReturn204WhenDeleteExistingUser() throws Exception {
        Mockito.when(findUserInteractor.execute(mockUserUUID)).thenReturn(mockUser);
        Mockito.doNothing().when(deleteUserInteractor).execute(mockUserUUID);

        mockMvc.perform(delete("/users/" + mockUserUUID)
                .contentType("application/json"))
                .andExpect(status().isNoContent());

        verify(deleteUserInteractor, times(1)).execute(mockUserUUID);
    }

    @Test
    @Description("Should return 404 http status when no exist user with provided id")
    void shouldReturn404WhenNoExistUserWithProvidedId() throws Exception {
        Mockito.doThrow(new NotFoundException("resource", mockUserUUID)).when(deleteUserInteractor).execute(mockUserUUID);

        MvcResult result = mockMvc.perform(delete("/users/" + mockUserUUID)
                .contentType("application/json"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound())
                .andReturn();

        verify(deleteUserInteractor, times(1)).execute(mockUserUUID);
        assertThat(result.getResponse().getContentAsString()).contains("Resource not found", mockUserUUID);
    }

}
