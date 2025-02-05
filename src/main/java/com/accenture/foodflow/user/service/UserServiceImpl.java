package com.accenture.foodflow.user.service;

import com.accenture.foodflow.user.dao.UserDao;
import com.accenture.foodflow.user.dto.UserResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

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
}

