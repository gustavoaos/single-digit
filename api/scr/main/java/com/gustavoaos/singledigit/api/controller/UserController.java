package com.gustavoaos.singledigit.api.controller;

import com.gustavoaos.singledigit.application.*;
import com.gustavoaos.singledigit.application.request.CreateUserRequest;
import com.gustavoaos.singledigit.application.request.ComputeSingleDigitRequest;
import com.gustavoaos.singledigit.application.request.UpdateUserRequest;
import com.gustavoaos.singledigit.application.response.UserResponse;
import com.gustavoaos.singledigit.domain.exception.NotFoundException;
import com.gustavoaos.singledigit.domain.exception.ParameterOutOfRangeException;
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
    private final UpdateUserInteractor updateUserInteractor;
    private final ComputeSingleDigitInteractor computeSingleDigitInteractor;

    @Autowired
    public UserController(
            CreateUserInteractor createUserInteractor,
            FindUserInteractor findUserInteractor,
            DeleteUserInteractor deleteUserInteractor,
            UpdateUserInteractor updateUserInteractor,
            ComputeSingleDigitInteractor computeSingleDigitInteractor
    ) {
        this.createUserInteractor = createUserInteractor;
        this.findUserInteractor = findUserInteractor;
        this.deleteUserInteractor = deleteUserInteractor;
        this.updateUserInteractor = updateUserInteractor;
        this.computeSingleDigitInteractor = computeSingleDigitInteractor;
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

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") String id) {
        try {
            this.deleteUserInteractor.execute(id);
        } catch (NotFoundException err) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, err.getMessage());
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseEntity<UserResponse> update(
            @PathVariable("id") String id,
            @RequestBody UpdateUserRequest request) {
        try {
            if (request.getEmail() == null && request.getName()  == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing argument");
            }
            UserResponse user = this.updateUserInteractor.execute(id, request);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } catch (NotFoundException err) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, err.getMessage());
        }
    }

    @GetMapping("/compute")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseEntity<Integer> compute(
            @RequestParam(name = "id", required = false) String id,
            @RequestBody ComputeSingleDigitRequest request) {
        try {
            if (request.getN() == null || request.getK() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing argument");
            }
            Integer sd;

            if (id != null) {
                sd  = this.computeSingleDigitInteractor.execute(request, id);
            } else {
                sd  = this.computeSingleDigitInteractor.execute(request);
            }

            return ResponseEntity.status(HttpStatus.OK).body(sd);
        } catch (ParameterOutOfRangeException | NotFoundException err) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, err.getMessage());
        }
    }

}
