package com.accenture.foodflow.auth.service.authentication;

import com.accenture.foodflow.auth.dto.LoginRequestDto;
import com.accenture.foodflow.auth.dto.LoginResponseDto;
import com.accenture.foodflow.auth.dto.RegisterRequestDto;
import com.accenture.foodflow.auth.mapper.AuthenticationMapper;
import com.accenture.foodflow.auth.service.jwt.JwtService;
import com.accenture.foodflow.common.exception.exceptions.InvalidUserException;
import com.accenture.foodflow.user.dao.UserDao;
import com.accenture.foodflow.user.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private final UserDao userDao;
    private final AuthenticationMapper authenticationMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public void register(RegisterRequestDto registerRequestDto) {
        var existingUser = userDao.findByEmail(registerRequestDto.getEmail());

        if (existingUser.isPresent()) {
            throw new InvalidUserException("User already exists");
        }

        userDao.saveUser(authenticationMapper.toUser(registerRequestDto));
    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword()));

        var user = userDao.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new InvalidUserException("User not found"));

        var jwtToken = jwtService.generateToken(user);

        return LoginResponseDto.builder()
                .token(jwtToken)
                .build();
    }

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || !authentication.isAuthenticated()) {
            throw new InvalidUserException("User not authenticated");
        }

        Object principal = authentication.getPrincipal();
        if (!(principal instanceof User user)) {
            throw new InvalidUserException("Authenticated principal is not a User.");
        }

        return user;
    }

}
