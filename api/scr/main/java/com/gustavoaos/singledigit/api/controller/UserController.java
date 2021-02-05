package com.gustavoaos.singledigit.api.controller;

import com.gustavoaos.singledigit.application.*;
import com.gustavoaos.singledigit.application.request.CreateUserRequest;
import com.gustavoaos.singledigit.application.request.ComputeSingleDigitRequest;
import com.gustavoaos.singledigit.application.request.UpdateUserRequest;
import com.gustavoaos.singledigit.application.response.SingleDigitListResponse;
import com.gustavoaos.singledigit.application.response.UserResponse;
import com.gustavoaos.singledigit.domain.exception.ArgumentOutOfRangeException;
import com.gustavoaos.singledigit.domain.exception.WrongKeyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final CreateUserInteractor createUserInteractor;
    private final FindUserInteractor findUserInteractor;
    private final DeleteUserInteractor deleteUserInteractor;
    private final UpdateUserInteractor updateUserInteractor;
    private final ComputeSingleDigitInteractor computeSingleDigitInteractor;
    private final ListSingleDigitsInteractor listSingleDigitsInteractor;

    @Autowired
    public UserController(
            CreateUserInteractor createUserInteractor,
            FindUserInteractor findUserInteractor,
            DeleteUserInteractor deleteUserInteractor,
            UpdateUserInteractor updateUserInteractor,
            ComputeSingleDigitInteractor computeSingleDigitInteractor,
            ListSingleDigitsInteractor listSingleDigitsInteractor
    ) {
        this.createUserInteractor = createUserInteractor;
        this.findUserInteractor = findUserInteractor;
        this.deleteUserInteractor = deleteUserInteractor;
        this.updateUserInteractor = updateUserInteractor;
        this.computeSingleDigitInteractor = computeSingleDigitInteractor;
        this.listSingleDigitsInteractor = listSingleDigitsInteractor;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody ResponseEntity<UserResponse> create(@Valid @RequestBody CreateUserRequest request) {
        UserResponse user = this.createUserInteractor.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseEntity<UserResponse> find(
            @RequestHeader(value = "public-key", required = false) String publicKey,
            @PathVariable("id") String id) {
        try {
            UserResponse user;
            if (publicKey == null) {
                user = this.findUserInteractor.execute(id);
            } else {
                user = this.findUserInteractor.execute(id, publicKey);
            }
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } catch (WrongKeyException err) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, err.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") String id) {
        this.deleteUserInteractor.execute(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseEntity<UserResponse> update(
            @PathVariable("id") String id,
            @RequestBody UpdateUserRequest request) {
        UserResponse user = this.updateUserInteractor.execute(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping("/compute")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseEntity<Integer> compute(
            @RequestParam(name = "id", required = false) String id,
            @Valid @RequestBody ComputeSingleDigitRequest request) {
        try {
            Integer sd;
            if (id == null) {
                sd  = this.computeSingleDigitInteractor.execute(request);
            } else {
                sd  = this.computeSingleDigitInteractor.execute(request, id);
            }
            return ResponseEntity.status(HttpStatus.OK).body(sd);
        } catch (ArgumentOutOfRangeException err) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, err.getMessage());
        }
    }

    @GetMapping("/{id}/list")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseEntity<SingleDigitListResponse> list(
            @PathVariable("id") String id) {
        SingleDigitListResponse list = this.listSingleDigitsInteractor.execute(id);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

}
