package com.accenture.foodflow.user.dao;

import com.accenture.foodflow.common.exception.exceptions.EntityNotFoundException;
import com.accenture.foodflow.user.entity.User;
import com.accenture.foodflow.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserDaoImpl implements UserDao {

    private final UserRepository userRepository;

    @Override
    public Optional<User> findByEmail(String email) {
        if(email == null) {
            throw new EntityNotFoundException("Email cannot be null");
        }

        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User>  findUserById(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }

        return userRepository.findById(id);
    }

    @Override
    public void saveUser(User user) {
        if(user == null) {
            throw new EntityNotFoundException("User cannot be null");
        }

        userRepository.save(user);
    }

    @Override
    public Optional<User> findById(UUID id) {
        if (id == null) {
            throw new EntityNotFoundException("Id cannot be null");
        }

        return userRepository.findById(id);
    }

}
