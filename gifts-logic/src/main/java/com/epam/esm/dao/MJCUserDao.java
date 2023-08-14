package com.epam.esm.dao;

import com.epam.esm.entity.User;

import java.util.List;
import java.util.Optional;

public interface MJCUserDao {
    Optional<User> findById(long id);

    List<User> findAll(int page, int pageSize);

    User update(User user);

    boolean addOrderToUser(long userId, long orderId);

    Optional<User> findByOrderId(long orderId);
}
