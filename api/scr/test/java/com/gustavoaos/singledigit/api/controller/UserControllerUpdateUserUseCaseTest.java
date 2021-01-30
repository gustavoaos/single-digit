package com.gustavoaos.singledigit.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
class UserControllerUpdateUserUseCaseTest {

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
    @Description("Should return 200 http status and user when valid id is provided")
    void shouldReturn200AndUserWhenValidIdIsProvided() throws Exception {
        Mockito.when(updateUserInteractor.execute(
                mockUser.getId(), request)
        ).thenReturn(updatedUser);

        mockMvc.perform(put("/users/" + mockUserUUID)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(updatedUser.getId())))
                .andExpect(jsonPath("$.name", is(updatedUser.getName())))
                .andExpect(jsonPath("$.email", is(updatedUser.getEmail())))
                .andExpect(jsonPath("$.singleDigits", hasSize(0)));
    }

    @Test
    @Description("Should return 404 http status when user not found")
    void shouldReturn404WhenUserNotFound() throws Exception {
        Mockito.when(updateUserInteractor.execute(
                mockUserUUID, request)
        ).thenThrow(new NotFoundException("resource", mockUserUUID));

        mockMvc.perform(put("/users/" + mockUserUUID)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound())
                .andExpect(status().reason("Resource not found"));
    }

    @Test
    @Description("Should return 400 if no name and email are provided")
    void shouldReturn400IfNoNameAndEmailAreProvided() throws Exception {
        mockMvc.perform(put("/users/" + mockUserUUID)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().isBadRequest());
    }

}
