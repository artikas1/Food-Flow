package com.accenture.foodflow.auth.controller;

import com.accenture.foodflow.auth.dto.LoginRequestDto;
import com.accenture.foodflow.auth.dto.LoginResponseDto;
import com.accenture.foodflow.auth.dto.RegisterRequestDto;
import com.accenture.foodflow.auth.service.authentication.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
@AllArgsConstructor
@Tag(name = "Authentication Controller", description = "APIs for user authentication and registration")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Operation(summary = "Register a new user", description = "Creates a new user account with the provided details")
    @PostMapping("/register")
    public ResponseEntity<Void> register(
            @Parameter(description = "User registration details") @Valid @RequestBody RegisterRequestDto registerRequestDto) {
        authenticationService.register(registerRequestDto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "User login", description = "Authenticates a user and returns a JWT token")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @Parameter(description = "User login credentials") @Valid @RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(authenticationService.login(loginRequestDto));
    }

}
