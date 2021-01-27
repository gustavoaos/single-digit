package com.gustavoaos.singledigit.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gustavoaos.singledigit.application.CreateUserInteractor;
import com.gustavoaos.singledigit.application.request.CreateUserRequest;
import com.gustavoaos.singledigit.application.response.UserResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Description;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @Test
    @Description("Should return 400 if no user is provided")
    void shouldReturn400IfNoUserIsProvided() throws Exception {
        mockMvc.perform(post("/users")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Description("Should return 201 status and created user if valid user is provided")
    void shouldReturn201IfValidUserIsProvided() throws Exception {
        CreateUserRequest userRequest = CreateUserRequest
                .builder()
                .name("Valid Name")
                .email("valid@mail.com")
                .build();
        UUID mockUserUUID = UUID.randomUUID();
        UserResponse mockUser = UserResponse
                .builder()
                .id(mockUserUUID)
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
                .andExpect(jsonPath("$.email", is(mockUser.getEmail())));
    }

}
