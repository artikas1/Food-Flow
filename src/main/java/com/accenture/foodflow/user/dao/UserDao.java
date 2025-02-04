package com.accenture.foodflow.user.dao;

import com.accenture.foodflow.user.entity.User;

import java.util.Optional;

public interface UserDao {

    Optional<User> getUserByEmail(String email);

}
