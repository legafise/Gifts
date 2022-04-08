package com.epam.esm.dao.impl;

import com.epam.esm.TestApplication;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
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
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
@ActiveProfiles("template-test")
@Transactional
class JdbcTemplateMJCUserDaoTest {
    @Autowired
    private JdbcTemplateMJCUserDao userDao;
    private User testUser;
    private List<User> testUserList;

    @BeforeEach
    void setUp() {
        testUser = new User(101, "Oleg", new BigDecimal("100.00"), new ArrayList<>());
        User secondTestUser = new User(102, "Ivan", new BigDecimal("75.78"), new ArrayList<>());

        testUserList = Arrays.asList(testUser, secondTestUser);
    }

    @Test
    void findByIdPositiveTest() {
        Assertions.assertEquals(testUser, userDao.findById(101).get());
    }

    @Test
    void findAllTest() {
        Assertions.assertEquals(testUserList, userDao.findAll(1, 2));
    }

    @Test
    void updateUserTest() {
        testUser.setBalance(new BigDecimal("50.00"));
        Assertions.assertEquals(testUser, userDao.update(testUser));
    }

    @Test
    void addOrderToUserTest() {
        Assertions.assertTrue(userDao.addOrderToUser(101, 103));
    }
}