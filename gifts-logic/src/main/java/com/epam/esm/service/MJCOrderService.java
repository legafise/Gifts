package com.epam.esm.service;

import com.epam.esm.entity.Order;

import java.util.List;

public interface MJCOrderService {
    Order findOrderById(long orderId);

    Order createOrder(long userId, long certificateId);

    List<Order> findOrdersByUserId(long userId);
}
