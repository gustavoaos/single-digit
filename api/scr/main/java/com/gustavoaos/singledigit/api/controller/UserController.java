package com.gustavoaos.singledigit.api.controller;

import com.gustavoaos.singledigit.application.CreateUserInteractor;
import com.gustavoaos.singledigit.application.DeleteUserInteractor;
import com.gustavoaos.singledigit.application.FindUserInteractor;
import com.gustavoaos.singledigit.application.request.CreateUserRequest;
import com.gustavoaos.singledigit.application.response.UserResponse;
import com.gustavoaos.singledigit.domain.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final CreateUserInteractor createUserInteractor;
    private final FindUserInteractor findUserInteractor;
    private final DeleteUserInteractor deleteUserInteractor;

    @Autowired
    public UserController(
            CreateUserInteractor createUserInteractor,
            FindUserInteractor findUserInteractor,
            DeleteUserInteractor deleteUserInteractor
    ) {
        this.createUserInteractor = createUserInteractor;
        this.findUserInteractor = findUserInteractor;
        this.deleteUserInteractor = deleteUserInteractor;
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

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseEntity<UserResponse> find(@PathVariable("id") String id) {
        try {
            UserResponse user = this.findUserInteractor.execute(id);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } catch (NotFoundException err) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, err.getMessage());
        }
    }

}
