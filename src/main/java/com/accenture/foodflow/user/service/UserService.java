package com.accenture.foodflow.user.service;

import com.accenture.foodflow.user.dto.UserResponseDto;

import java.util.UUID;

public interface UserService {
    UserResponseDto getUserById(UUID id);
}
