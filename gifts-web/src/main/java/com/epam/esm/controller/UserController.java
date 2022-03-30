package com.epam.esm.controller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.facade.MJCUserFacade;
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
    private static final String ORDERED_CERTIFICATE_INFO = "Ordered certificate(id = %d) info";
    private final MJCUserFacade userFacade;

    @Autowired
    public UserController(MJCUserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public EntityModel<UserDto> readUserById(@PathVariable long id) {
        UserDto foundUser = userFacade.findUserById(id);

        return createHateoasUserModel(foundUser);
    }

    @GetMapping
    @ResponseStatus(OK)
    public List<EntityModel<UserDto>> readAllUsers(@RequestParam Map<String, String> paginationParameters) {
        List<UserDto> userList = userFacade.findAllUsers(paginationParameters);

        return userList.stream()
                .map(this::createHateoasUserModel)
                .collect(Collectors.toList());
    }

    @GetMapping("/{userId}/orders")
    @ResponseStatus(OK)
    public List<EntityModel<OrderDto>> readAllUserOrders(@PathVariable long userId) {
        List<OrderDto> userOrders = userFacade.findUserById(userId).getOrders();
        return userOrders.stream()
                .map(EntityModel::of)
                .peek(orderEntityModel -> {
                    WebMvcLinkBuilder linkToOrderedCertificate = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CertificateController.class)
                            .readCertificateById(Objects.requireNonNull(orderEntityModel.getContent()).getCertificate().getId()));
                    orderEntityModel.add(linkToOrderedCertificate.withRel(String.format(ORDERED_CERTIFICATE_INFO,
                            orderEntityModel.getContent().getCertificate().getId())));

                })
                .collect(Collectors.toList());
    }

    private EntityModel<UserDto> createHateoasUserModel(UserDto user) {
        EntityModel<UserDto> userEntityModel = EntityModel.of(user);
        Objects.requireNonNull(userEntityModel.getContent()).getOrders()
                .forEach(order -> {
                    WebMvcLinkBuilder linkToOrder = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
                            .methodOn(OrderController.class).readOrder(order.getId()));
                    userEntityModel.add(linkToOrder.withRel(String.format(ORDER_INFO_MESSAGE, order.getId())));
                });

        return userEntityModel;
    }
}
