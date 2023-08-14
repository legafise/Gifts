package com.epam.esm.controller;

import com.epam.esm.MJCApplication;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.service.exception.MJCUnknownEntityException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MJCApplication.class)
@ActiveProfiles("template-test")
@Transactional
class OrderControllerJdbcTemplateTest {
    @Autowired
    private MJCOrderController orderController;
    private EntityModel<OrderDto> testOrderEntityModel;

    @BeforeEach
    void setUp() {
        TagDto testTagDto = new TagDto(101, "Tattoo");
        OrderDto testOrderDto = new OrderDto(101, new CertificateDto(101, "TattooLand", "The certificate allows to you make a tattoo",
                new BigDecimal("125.00"), (short) 92, LocalDateTime.parse("2022-01-20T21:00"),
                LocalDateTime.parse("2022-04-20T21:00"), Collections.singletonList(testTagDto)),
                new BigDecimal("125.00"), LocalDateTime.parse("2022-03-20T17:14:42"));

        testOrderEntityModel = EntityModel.of(testOrderDto);
        testOrderEntityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MJCUserController.class)
                .readUserById(101)).withRel("Customer(id = 101) info"));
        testOrderEntityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MJCCertificateController.class)
                .readCertificateById(testOrderDto.getCertificate().getId())).withRel("Ordered certificate(id = 101) info"));
    }

    @Test
    void readOrderTest() {
        Assertions.assertEquals(testOrderEntityModel, orderController.readOrder(101));
    }

    @Test
    void readOrderWithInvalidIdTest() {
        Assertions.assertThrows(MJCUnknownEntityException.class, () -> orderController.readOrder(749));
    }
}