package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.service.UserService;
import com.epam.esm.service.exception.UnknownEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private static final String NONEXISTENT_USER_MESSAGE = "nonexistent.user";
    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User findUserById(long id) {
        Optional<User> user = userDao.findById(id);
        if (!user.isPresent()) {
            throw new UnknownEntityException(Order.class, NONEXISTENT_USER_MESSAGE);
        }

        return user.get();
    }

    @Override
    public List<User> findAllUsers() {
        return userDao.findAll();
    }
}
