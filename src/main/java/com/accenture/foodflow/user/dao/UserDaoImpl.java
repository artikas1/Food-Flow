package com.accenture.foodflow.user.dao;

import com.accenture.foodflow.user.entity.User;
import com.accenture.foodflow.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDaoImpl implements UserDao {

    private final UserRepository userRepository;

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email); //TODO add null check with custom exception
    }

}
