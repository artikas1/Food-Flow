package com.accenture.foodflow.auth.service.authentication;

import com.accenture.foodflow.auth.dto.LoginRequestDto;
import com.accenture.foodflow.auth.dto.LoginResponseDto;
import com.accenture.foodflow.auth.dto.RegisterRequestDto;
import com.accenture.foodflow.user.entity.User;

public interface AuthenticationService {

    void register(RegisterRequestDto registerRequestDto);
    LoginResponseDto login(LoginRequestDto loginRequestDto);
    User getAuthenticatedUser();

}
