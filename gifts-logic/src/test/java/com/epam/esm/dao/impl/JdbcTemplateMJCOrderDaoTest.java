package com.epam.esm.dao.impl;

import com.epam.esm.TestApplication;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
@ActiveProfiles("template-test")
@Transactional
class JdbcTemplateMJCOrderDaoTest {
    @Autowired
    private JdbcTemplateMJCOrderDao orderDao;
    private Order testOrder;

    @BeforeEach
    void setUp() {
        testOrder = new Order(101,  new Certificate(101, "TattooLand", "The certificate allows to you make a tattoo",
                new BigDecimal("125.00"), (short) 92, LocalDateTime.parse("2022-01-20T21:00"),
                LocalDateTime.parse("2022-04-20T21:00"), new HashSet<>()),
                new BigDecimal("125.00"), LocalDateTime.parse("2022-03-20T17:14:42"));
    }

    @Test
    void findByIdPositiveTest() {
        Assertions.assertEquals(testOrder, orderDao.findById(101).get());
    }

    @Test
    void findByOrderWithInvalidIdTest() {
        Assertions.assertFalse(orderDao.findById(355).isPresent());
    }

    @Test
    void findMaxOrderIdTest() {
        Assertions.assertEquals(103, orderDao.findMaxOrderId());
    }

    @Test
    void findOrdersByUserIdTest() {
        Assertions.assertEquals(Collections.singletonList(testOrder), orderDao.findOrdersByUserId(101));
    }

    @Test
    void findOrdersByUserIdWhenUserDoesNotHaveOrdersTest() {
        Assertions.assertEquals(Collections.EMPTY_LIST, orderDao.findOrdersByUserId(103));
    }
}