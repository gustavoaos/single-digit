package com.gustavoaos.singledigit.api.controller;

import com.gustavoaos.singledigit.application.CreateUserInteractor;
import com.gustavoaos.singledigit.application.request.CreateUserRequest;
import com.gustavoaos.singledigit.application.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final CreateUserInteractor createUserInteractor;

    @Autowired
    public UserController(CreateUserInteractor createUserInteractor) {
        this.createUserInteractor = createUserInteractor;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody ResponseEntity<UserResponse> create(@RequestBody CreateUserRequest request) {
        if (request.getEmail() == null || request.getName()  == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing argument");
        }

        UserResponse user = this.createUserInteractor.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

}
