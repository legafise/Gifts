package com.epam.esm.controller;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public User readUserById(@PathVariable long id) {
        return userService.findUserById(id);
    }

    @GetMapping
    @ResponseStatus(OK)
    public List<User> readAllUsers(@RequestParam Map<String, String> paginationParameters) {
        return userService.findAllUsers(paginationParameters);
    }

    @GetMapping("/{userId}/orders")
    @ResponseStatus(OK)
    public List<Order> readAllUserOrders(@PathVariable long userId) {
        return userService.findUserById(userId).getOrders();
    }
}
