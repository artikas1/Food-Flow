package com.accenture.foodflow.user.dao;

import com.accenture.foodflow.user.entity.User;
import java.util.Optional;
import java.util.UUID;

public interface UserDao {

    Optional<User> findByEmail(String email);
    void saveUser(User user);
    Optional<User> findById(UUID id);

}
