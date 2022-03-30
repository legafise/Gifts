package com.epam.esm.service;

import com.epam.esm.entity.Order;

public interface MJCOrderService {
    Order findOrderById(long orderId);

    Order createOrder(long userId, long certificateId);
}
