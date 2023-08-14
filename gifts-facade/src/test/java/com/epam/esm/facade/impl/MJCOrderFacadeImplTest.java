package com.epam.esm.facade.impl;

import com.epam.esm.converter.MJCConverter;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import com.epam.esm.facade.MJCOrderFacade;
import com.epam.esm.service.MJCOrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MJCOrderFacadeImplTest {
    private MJCOrderFacadeImpl orderFacade;
    private MJCOrderService orderService;
    private MJCConverter<Order, OrderDto> orderConverter;
    private Order testOrder;
    private OrderDto testOrderDto;

    @BeforeEach
    void setUp() {
        orderFacade = new MJCOrderFacadeImpl();
        orderService = mock(MJCOrderService.class);
        orderConverter = (MJCConverter<Order, OrderDto>) mock(MJCConverter.class);
        orderFacade.setOrderConverter(orderConverter);
        orderFacade.setOrderService(orderService);

        Tag testTag = new Tag(1, "Tattoo");
        testOrder = new Order(1, new Certificate(1, "TattooLand", "The certificate allows to you make a tattoo",
                new BigDecimal("125.00"), (short) 92, LocalDateTime.parse("2022-01-20T21:00"),
                LocalDateTime.parse("2022-04-20T21:00"), new HashSet<>(Collections.singletonList(testTag))),
                new BigDecimal("125.00"), LocalDateTime.parse("2022-03-20T17:14:42"));

        TagDto testTagDto = new TagDto(1, "Tattoo");
        testOrderDto = new OrderDto(1, new CertificateDto(1, "TattooLand", "The certificate allows to you make a tattoo",
                new BigDecimal("125.00"), (short) 92, LocalDateTime.parse("2022-01-20T21:00"),
                LocalDateTime.parse("2022-04-20T21:00"), Collections.singletonList(testTagDto)),
                new BigDecimal("125.00"), LocalDateTime.parse("2022-03-20T17:14:42"));
    }

    @Test
    void findOrderByIdTest() {
        when(orderService.findOrderById(1)).thenReturn(testOrder);
        when(orderConverter.convert(testOrder)).thenReturn(testOrderDto);
        Assertions.assertEquals(testOrderDto, orderFacade.findOrderById(1));
    }

    @Test
    void createOrderTest() {
        when(orderService.createOrder(1, 1)).thenReturn(testOrder);
        when(orderConverter.convert(testOrder)).thenReturn(testOrderDto);
        Assertions.assertEquals(testOrderDto, orderFacade.createOrder(1, 1));
    }
}