package com.accenture.foodflow.user.service;

import com.accenture.foodflow.user.dto.ChangePasswordRequestDto;
import com.accenture.foodflow.user.dto.UserResponseDto;

import java.util.UUID;

public interface UserService {
    UserResponseDto getUser();
    void changePassword(ChangePasswordRequestDto changePasswordRequestDto);
    UserResponseDto getUserById(UUID id);

}
