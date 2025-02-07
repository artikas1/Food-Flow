package com.accenture.foodflow.user.controller;

import com.accenture.foodflow.user.dto.ChangePasswordRequestDto;
import com.accenture.foodflow.user.dto.UserResponseDto;
import com.accenture.foodflow.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@Tag(name = "User Controller", description = "APIs for user management and authentication")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Get user by ID", description = "Retrieves user details by their unique ID")
    @GetMapping("/{id}")
    public UserResponseDto getUserById(
            @Parameter(description = "Unique ID of the user") @PathVariable UUID id) {
        return userService.getUserById(id);
    }

    @Operation(summary = "Change user password", description = "Allows a user to change their password")
    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(
            @Parameter(description = "Request body containing old and new passwords") @RequestBody ChangePasswordRequestDto changePasswordRequestDto) {
        userService.changePassword(changePasswordRequestDto);
        return ResponseEntity.ok("Password changed successfully");
    }

}
