package com.epam.esm.service;

import com.epam.esm.entity.User;

import java.util.List;
import java.util.Map;

public interface MJCUserService {
    User findUserById(long id);

    List<User> findAllUsers(Map<String, String> paginationParameters);

    User findUserByOrderId(long orderId);
}
