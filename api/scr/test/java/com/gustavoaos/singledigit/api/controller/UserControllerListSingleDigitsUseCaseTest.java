package com.gustavoaos.singledigit.api.controller;

import com.gustavoaos.singledigit.application.*;
import com.gustavoaos.singledigit.application.request.UpdateUserRequest;
import com.gustavoaos.singledigit.application.response.UserResponse;
import com.gustavoaos.singledigit.domain.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Description;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Collections;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
public class UserControllerListSingleDigitsUseCaseTest {

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
    @Description("Should return 404 http status when user not found")
    void shouldReturn404WhenUserNotFound() throws Exception {
        Mockito.when(listSingleDigitsInteractor.execute(
                mockUserUUID)
        ).thenThrow(new NotFoundException("resource", mockUserUUID));

        mockMvc.perform(get("/users/" + mockUserUUID + "/list")
                .contentType("application/json"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound())
                .andExpect(status().reason("Resource not found"));
    }
}
