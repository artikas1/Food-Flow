package com.accenture.foodflow.auth.mapper;

import com.accenture.foodflow.auth.dto.RegisterRequestDto;
import com.accenture.foodflow.user.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@AllArgsConstructor
public class AuthenticationMapper {

    private final PasswordEncoder passwordEncoder;

    public User toUser(RegisterRequestDto registerRequest) {
        return User.builder()
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .firstName(registerRequest.getName())
                .lastName(registerRequest.getSurname())
                .birthDate(registerRequest.getBirthDate())
                .gender(registerRequest.getGender())
                .build();
    }

}
