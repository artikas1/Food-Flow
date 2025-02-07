package com.accenture.foodflow.user.controller;

import com.accenture.foodflow.user.dto.ChangePasswordRequestDto;
import com.accenture.foodflow.user.dto.UserResponseDto;
import com.accenture.foodflow.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public UserResponseDto getUserById(@PathVariable UUID id) {
        return userService.getUserById(id);
    }

    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequestDto changePasswordRequestDto) {
        userService.changePassword(changePasswordRequestDto);
        return ResponseEntity.ok("Password changed successfully");
    }
}
