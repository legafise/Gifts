package com.epam.esm.controller;

import com.epam.esm.MJCApplication;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.service.constant.MJCPaginationConstant;
import com.epam.esm.service.exception.MJCInvalidPaginationDataException;
import com.epam.esm.service.exception.MJCMissingPageNumberException;
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
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MJCApplication.class)
@ActiveProfiles("template-test")
@Transactional
class UserControllerJdbcTemplateTest {
    @Autowired
    private MJCUserController userController;
    private EntityModel<UserDto> testUserModel;
    private List<EntityModel<OrderDto>> testUserOrdersModelList;
    private List<EntityModel<UserDto>> testUserModelList;

    @BeforeEach
    void setUp() {
        TagDto testTag = new TagDto(101, "Tattoo");
        TagDto secondTestTag = new TagDto(102,"Jumps");
        TagDto thirdTestTag = new TagDto(103,"Entertainment");

        OrderDto testOrderDto = new OrderDto(101,  new CertificateDto(101, "TattooLand", "The certificate allows to you make a tattoo",
                new BigDecimal("125.00"), (short) 92, LocalDateTime.parse("2022-01-20T21:00"),
                LocalDateTime.parse("2022-04-20T21:00"), Collections.singletonList(testTag)),
                new BigDecimal("125.00"), LocalDateTime.parse("2022-03-20T17:14:42"));
        UserDto testUserDto = new UserDto(101, "Oleg", new BigDecimal("100.00"), Collections.singletonList(testOrderDto));
        testUserModel = EntityModel.of(testUserDto);
        testUserModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MJCOrderController.class)
                .readOrder(101)).withRel("Order(id = 101) info"));

        OrderDto secondTestOrderDto = new OrderDto(102, new CertificateDto(102, "Jump park", "Free jumps at trampolines",
                new BigDecimal("35.00"), (short) 30, LocalDateTime.parse("2022-03-15T21:30"),
                LocalDateTime.parse("2022-06-15T21:30"), Arrays.asList(secondTestTag, thirdTestTag)),
                new BigDecimal("30.00"), LocalDateTime.parse("2022-04-20T15:12:34"));
        UserDto secondTestUserDto = new UserDto(102, "Ivan", new BigDecimal("75.78"), Collections.singletonList(secondTestOrderDto));
        EntityModel<UserDto> secondTestUserModel = EntityModel.of(secondTestUserDto);
        secondTestUserModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MJCOrderController.class)
                .readOrder(102)).withRel("Order(id = 102) info"));

        EntityModel<OrderDto> testOrderEntityModel = EntityModel.of(testOrderDto);
        testOrderEntityModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MJCCertificateController.class)
                .readCertificateById(testOrderDto.getCertificate().getId())).withRel("Ordered certificate(id = 101) info"));

        testUserModelList = Arrays.asList(testUserModel, secondTestUserModel);
        testUserOrdersModelList = Collections.singletonList(testOrderEntityModel);
    }

    @Test
    void readUserByIdTest() {
        Assertions.assertEquals(testUserModel, userController.readUserById(101));
    }

    @Test
    void readUserWithInvalidIdTest() {
        Assertions.assertThrows(MJCUnknownEntityException.class, () -> userController.readUserById(974));
    }

    @Test
    void readAllUsersTest() {
        Map<String, String> pageParameters = new HashMap<>();
        pageParameters.put(MJCPaginationConstant.PAGE_PARAMETER, "1");
        pageParameters.put(MJCPaginationConstant.PAGE_SIZE_PARAMETER, "2");

        Assertions.assertEquals(testUserModelList, userController.readAllUsers(pageParameters));
    }

    @Test
    void readAllUsersWithInvalidPageParameterTest() {
        Map<String, String> pageParameters = new HashMap<>();
        pageParameters.put(MJCPaginationConstant.PAGE_PARAMETER, "-17");
        pageParameters.put(MJCPaginationConstant.PAGE_SIZE_PARAMETER, "2");

        Assertions.assertThrows(MJCInvalidPaginationDataException.class, () -> userController.readAllUsers(pageParameters));
    }

    @Test
    void readAllUsersWithInvalidPageSizeParameterTest() {
        Map<String, String> pageParameters = new HashMap<>();
        pageParameters.put(MJCPaginationConstant.PAGE_PARAMETER, "1");
        pageParameters.put(MJCPaginationConstant.PAGE_SIZE_PARAMETER, "-15");

        Assertions.assertThrows(MJCInvalidPaginationDataException.class, () -> userController.readAllUsers(pageParameters));
    }

    @Test
    void readAllUsersWithoutPaginationParametersTest() {
        Assertions.assertThrows(MJCMissingPageNumberException.class, () -> userController.readAllUsers(new HashMap<>()));
    }

    @Test
    void readAllUserOrdersTest() {
        Assertions.assertEquals(testUserOrdersModelList, userController.readAllUserOrders(101));
    }

    @Test
    void readAllUserOrdersWithInvalidUserIdTest() {
        Assertions.assertThrows(MJCUnknownEntityException.class, () -> userController.readAllUserOrders(238));
    }
}