package com.epam.esm.controller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.facade.MJCOrderFacade;
import com.epam.esm.facade.MJCUserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/orders")
public class MJCOrderController {
    private static final String ORDERED_CERTIFICATE_INFO = "Ordered certificate(id = %d) info";
    private static final String CUSTOMER_INFO = "Customer(id = %d) info";
    private final MJCOrderFacade orderFacade;
    private final MJCUserFacade userFacade;

    @Autowired
    public MJCOrderController(MJCOrderFacade orderFacade, MJCUserFacade userFacade) {
        this.orderFacade = orderFacade;
        this.userFacade = userFacade;
    }

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public EntityModel<OrderDto> readOrder(@PathVariable long id) {
        OrderDto readOrder = orderFacade.findOrderById(id);
        long customerId = userFacade.findUserByOrderId(readOrder.getId()).getId();
        EntityModel<OrderDto> orderEntityModel = EntityModel.of(readOrder);
        WebMvcLinkBuilder linkToOrderedCertificate = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MJCCertificateController.class)
                .readCertificateById(Objects.requireNonNull(orderEntityModel.getContent()).getCertificate().getId()));
        WebMvcLinkBuilder linkToCustomer = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MJCUserController.class)
                .readUserById(customerId));
        orderEntityModel.add(linkToCustomer.withRel(String.format(CUSTOMER_INFO, customerId)));
        orderEntityModel.add(linkToOrderedCertificate.withRel(String.format(ORDERED_CERTIFICATE_INFO,
                orderEntityModel.getContent().getCertificate().getId())));

        return orderEntityModel;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public EntityModel<OrderDto> createOrder(@RequestParam long userId, @RequestParam long certificateId) {
        OrderDto createdOrder = orderFacade.createOrder(userId, certificateId);
        EntityModel<OrderDto> orderModel = EntityModel.of(createdOrder);
        WebMvcLinkBuilder linkToCustomer = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MJCUserController.class)
                .readUserById(userId));
        WebMvcLinkBuilder linkToAllCustomerOrders = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MJCUserController.class)
                .readAllUserOrders(userId));
        orderModel.add(linkToCustomer.withRel("customer"));
        orderModel.add(linkToAllCustomerOrders.withRel("all-customer's-orders"));
        return orderModel;
    }
}
