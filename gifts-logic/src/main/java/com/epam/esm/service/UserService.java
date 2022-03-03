package com.epam.esm.service;

import com.epam.esm.entity.User;

import java.util.List;

public interface UserService {
    User findUserById(long id);
    List<User> findAllUsers();
}
