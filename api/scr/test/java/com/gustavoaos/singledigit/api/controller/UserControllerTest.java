package com.gustavoaos.singledigit.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gustavoaos.singledigit.application.CreateUserInteractor;
import com.gustavoaos.singledigit.application.DeleteUserInteractor;
import com.gustavoaos.singledigit.application.FindUserInteractor;
import com.gustavoaos.singledigit.application.UpdateUserInteractor;
import com.gustavoaos.singledigit.application.request.CreateUserRequest;
import com.gustavoaos.singledigit.application.request.UpdateUserRequest;
import com.gustavoaos.singledigit.application.response.UserResponse;
import com.gustavoaos.singledigit.domain.SingleDigit;
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

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

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
    @Description("Should return 400 if no user is provided")
    void shouldReturn400IfNoUserIsProvided() throws Exception {
        mockMvc.perform(post("/users")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Description("Should return 400 if user with no email is provided")
    void shouldReturn400IfUserWithNoEmailIsProvided() throws Exception {
        CreateUserRequest userRequest = CreateUserRequest
                .builder()
                .name("Valid Name")
                .build();

        mockMvc.perform(post("/users")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Description("Should return 400 if user with no name is provided")
    void shouldReturn400IfUserWithNoNameIsProvided() throws Exception {
        CreateUserRequest userRequest = CreateUserRequest
                .builder()
                .email("valid@mail.com")
                .build();

        mockMvc.perform(post("/users")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Description("Should return 201 http status and created user when valid user is provided")
    void shouldReturn201IfValidUserIsProvided() throws Exception {
        CreateUserRequest userRequest = CreateUserRequest
                .builder()
                .name("Valid Name")
                .email("valid@mail.com")
                .build();

        Mockito.when(createUserInteractor.execute(Mockito.any())).thenReturn(mockUser);

        mockMvc.perform(post("/users")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userRequest)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(mockUser.getId().toString())))
                .andExpect(jsonPath("$.name", is(mockUser.getName())))
                .andExpect(jsonPath("$.email", is(mockUser.getEmail())))
                .andExpect(jsonPath("$.singleDigits", hasSize(0)));
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
                new SingleDigit("1", "1"),
                new SingleDigit("7894", "4"));
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

        mockMvc.perform(get("/users/" + mockUserUUID)
                .contentType("application/json"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound())
                .andExpect(status().reason("Resource not found"));
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

        mockMvc.perform(delete("/users/" + mockUserUUID)
                .contentType("application/json"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound())
                .andExpect(status().reason("Resource not found"));

        verify(deleteUserInteractor, times(1)).execute(mockUserUUID);
    }
}
