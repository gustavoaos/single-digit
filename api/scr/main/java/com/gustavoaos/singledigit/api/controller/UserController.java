package com.gustavoaos.singledigit.api.controller;

import com.gustavoaos.singledigit.application.*;
import com.gustavoaos.singledigit.application.request.CreateUserRequest;
import com.gustavoaos.singledigit.application.request.ComputeSingleDigitRequest;
import com.gustavoaos.singledigit.application.request.UpdateUserRequest;
import com.gustavoaos.singledigit.application.response.SingleDigitListResponse;
import com.gustavoaos.singledigit.application.response.UserResponse;
import com.gustavoaos.singledigit.domain.error.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "Get user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found user",
                content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = UserResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid arguments provided",
                content = {  @Content(mediaType = "application/json",
                schema = @Schema(implementation = ApiError.class))
            }),
            @ApiResponse(responseCode = "404", description = "User not found",
                content = {  @Content(mediaType = "application/json",
                schema = @Schema(implementation = ApiError.class))
            })
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseEntity<UserResponse> find(
            @RequestHeader(value = "public-key", required = false) String publicKey,
            @PathVariable("id") String id) {
        UserResponse user;
        if (publicKey == null) {
            user = this.findUserInteractor.execute(id);
        } else {
            user = this.findUserInteractor.execute(id, publicKey);
        }
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @Operation(summary = "Create user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created user",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid arguments provided",
                    content = {  @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiError.class))
            })
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody ResponseEntity<UserResponse> create(@Valid @RequestBody CreateUserRequest request) {
        UserResponse user = this.createUserInteractor.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @Operation(summary = "Delete user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleted user",
                content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = UserResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "User not found",
                content = {  @Content(mediaType = "application/json",
                schema = @Schema(implementation = ApiError.class))
            })
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") String id) {
        this.deleteUserInteractor.execute(id);
    }

    @Operation(summary = "Update user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated user",
                content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = UserResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid arguments provided",
                content = {  @Content(mediaType = "application/json",
                schema = @Schema(implementation = ApiError.class))
            }),
            @ApiResponse(responseCode = "404", description = "User not found",
                content = {  @Content(mediaType = "application/json",
                schema = @Schema(implementation = ApiError.class))
            })
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseEntity<UserResponse> update(
            @PathVariable("id") String id,
            @RequestBody UpdateUserRequest request) {
        UserResponse user = this.updateUserInteractor.execute(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @Operation(summary = "Compute single digit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Computed single digit",
                content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = Integer.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid arguments provided",
                content = {  @Content(mediaType = "application/json",
                schema = @Schema(implementation = ApiError.class))
            }),
            @ApiResponse(responseCode = "404", description = "User not found",
                content = {  @Content(mediaType = "application/json",
                schema = @Schema(implementation = ApiError.class))
            })
    })
    @GetMapping("/compute")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseEntity<Integer> compute(
            @RequestParam(name = "id", required = false) String id,
            @Valid @RequestBody ComputeSingleDigitRequest request) {
        Integer sd;
        if (id == null) {
            sd  = this.computeSingleDigitInteractor.execute(request);
        } else {
            sd  = this.computeSingleDigitInteractor.execute(request, id);
        }
        return ResponseEntity.status(HttpStatus.OK).body(sd);
    }

    @Operation(summary = "Get single digit list for user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Computed single digit list",
                content = { @Content(mediaType = "application/json",
                schema = @Schema(implementation = SingleDigitListResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "User not found",
                content = {  @Content(mediaType = "application/json",
                schema = @Schema(implementation = ApiError.class))
            })
    })
    @GetMapping("/{id}/list")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseEntity<SingleDigitListResponse> list(
            @PathVariable("id") String id) {
        SingleDigitListResponse list = this.listSingleDigitsInteractor.execute(id);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

}
