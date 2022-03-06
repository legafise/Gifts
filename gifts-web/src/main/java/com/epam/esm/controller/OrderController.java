package com.epam.esm.controller;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public Order readOrder(@PathVariable long id) {
        return orderService.findOrderById(id);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Order createOrder(@RequestParam long userId, @RequestParam long certificateId) {
        return orderService.createOrder(userId, certificateId);
    }
}
