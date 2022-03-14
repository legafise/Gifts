package com.epam.esm.controller;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final String ORDER_INFO_MESSAGE = "Order(id = %d) info";
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public EntityModel<User> readUserById(@PathVariable long id) {
        User foundUser = userService.findUserById(id);

        return createHateoasUserModel(foundUser);
    }

    @GetMapping
    @ResponseStatus(OK)
    public List<EntityModel<User>> readAllUsers(@RequestParam Map<String, String> paginationParameters) {
        List<User> userList = userService.findAllUsers(paginationParameters);

        return userList.stream()
                .map(this::createHateoasUserModel)
                .collect(Collectors.toList());
    }

    @GetMapping("/{userId}/orders")
    @ResponseStatus(OK)
    public List<Order> readAllUserOrders(@PathVariable long userId) {
        return userService.findUserById(userId).getOrders();
    }

    private EntityModel<User> createHateoasUserModel(User user) {
        EntityModel<User> userEntityModel = EntityModel.of(user);
        Objects.requireNonNull(userEntityModel.getContent()).getOrders()
                .forEach(order -> {
                    WebMvcLinkBuilder linkToOrder = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
                            .methodOn(OrderController.class).readOrder(order.getId()));
                    userEntityModel.add(linkToOrder.withRel(String.format(ORDER_INFO_MESSAGE, order.getId())));
                });
        
        return userEntityModel;
    }
}
