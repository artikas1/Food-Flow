package com.accenture.foodflow.user.dao;

import com.accenture.foodflow.common.exception.exceptions.InvalidUserException;
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
    public Optional<User> findByEmail(String email) {
        if(email == null) {
            throw new InvalidUserException("Email cannot be null");
        }

        return userRepository.findByEmail(email);
    }

    @Override
    public void saveUser(User user) {
        if(user == null) {
            throw new InvalidUserException("User cannot be null");
        }

        userRepository.save(user);
    }

}
