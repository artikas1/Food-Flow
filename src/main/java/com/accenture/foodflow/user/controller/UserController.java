package com.accenture.foodflow.user.controller;

import com.accenture.foodflow.user.dto.UserResponseDto;
import com.accenture.foodflow.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
