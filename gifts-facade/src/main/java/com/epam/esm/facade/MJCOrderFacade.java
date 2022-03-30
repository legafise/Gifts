package com.epam.esm.facade;

import com.epam.esm.dto.OrderDto;

public interface MJCOrderFacade {
    OrderDto findOrderById(long orderId);

    OrderDto createOrder(long userId, long certificateId);
}
