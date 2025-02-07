package com.accenture.foodflow.user.service;

import com.accenture.foodflow.auth.service.authentication.AuthenticationService;
import com.accenture.foodflow.user.dao.UserDao;
import com.accenture.foodflow.user.dto.ChangePasswordRequestDto;
import com.accenture.foodflow.user.dto.UserResponseDto;
import com.accenture.foodflow.user.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationService authenticationService;

    @Override
    public UserResponseDto getUserById(UUID id) {
        validateUserId(id);

        var optionalUser = userDao.findUserById(id);

        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }

        var user = optionalUser.get();

        return UserResponseDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .birthDate(user.getBirthDate())
                .gender(user.getGender())
                .build();
    }

    private void validateUserId(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
    }

    @Override
    public void changePassword(ChangePasswordRequestDto changePasswordRequestDto) {

        User currentUser = authenticationService.getAuthenticatedUser();

        if (!passwordEncoder.matches(changePasswordRequestDto.getCurrentPassword(), currentUser.getPassword())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }

        if (!changePasswordRequestDto.getNewPassword().equals(changePasswordRequestDto.getConfirmNewPassword())) {
            throw new IllegalArgumentException("New password and confirmation password do not match");
        }

        currentUser.setPassword(passwordEncoder.encode(changePasswordRequestDto.getNewPassword()));

        userDao.saveUser(currentUser);
    }

}

