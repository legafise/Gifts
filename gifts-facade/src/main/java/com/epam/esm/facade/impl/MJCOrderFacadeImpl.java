package com.epam.esm.facade.impl;

import com.epam.esm.converter.MJCConverter;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.Order;
import com.epam.esm.facade.MJCOrderFacade;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class MJCOrderFacadeImpl implements MJCOrderFacade {
    @Autowired
    @Qualifier("mjcOrderConverter")
    private MJCConverter<Order, OrderDto> orderConverter;
    @Autowired
    private OrderService orderService;

    public void setOrderConverter(MJCConverter<Order, OrderDto> orderConverter) {
        this.orderConverter = orderConverter;
    }

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public OrderDto findOrderById(long orderId) {
        return orderConverter.convert(orderService.findOrderById(orderId));
    }

    @Override
    public OrderDto createOrder(long userId, long certificateId) {
        return orderConverter.convert(orderService.createOrder(userId, certificateId));
    }
}
