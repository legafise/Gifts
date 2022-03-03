package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.User;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User findUserById(long id) {
        Optional<User> user = userDao.findById(id);
        if (!user.isPresent()) {
            throw new RuntimeException();
        }

        return user.get();
    }

    @Override
    public List<User> findAllUsers() {
        return userDao.findAll();
    }
}
