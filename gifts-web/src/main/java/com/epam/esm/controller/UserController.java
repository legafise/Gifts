package com.epam.esm.controller;

import com.epam.esm.entity.User;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<User> readAllUsers() {
        return userService.findAllUsers();
    }
}
