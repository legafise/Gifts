package com.epam.esm.facade.impl;

import com.epam.esm.converter.MJCConverter;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.service.MJCUserService;
import com.epam.esm.service.constant.MJCPaginationConstant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MJCUserFacadeImplTest {
    private MJCUserFacadeImpl userFacade;
    private MJCUserService userService;
    private MJCConverter<User, UserDto> userConverter;
    private User testUser;
    private UserDto testUserDto;
    private List<User> testUserList;
    private List<UserDto> testUserDtoList;

    @BeforeEach
    void setUp() {
        userFacade = new MJCUserFacadeImpl();
        userService = mock(MJCUserService.class);
        userConverter = (MJCConverter<User, UserDto>) mock(MJCConverter.class);
        userFacade.setUserConvertor(userConverter);
        userFacade.setUserService(userService);

        Tag testTag = new Tag(1, "Tattoo");
        Tag secondTestTag = new Tag(2,"Jumps");
        Tag thirdTestTag = new Tag(3,"Entertainment");

        TagDto testTagDto = new TagDto(1, "Tattoo");
        TagDto secondTestTagDto = new TagDto(2,"Jumps");
        TagDto thirdTestTagDto = new TagDto(3,"Entertainment");

        Order testOrder = new Order(1,  new Certificate(1, "TattooLand", "The certificate allows to you make a tattoo",
                new BigDecimal("125.00"), (short) 92, LocalDateTime.parse("2022-01-20T21:00"),
                LocalDateTime.parse("2022-04-20T21:00"), new HashSet<>(Collections.singletonList(testTag))),
                new BigDecimal("125.00"), LocalDateTime.parse("2022-03-20T17:14:42"));
        testUser = new User(1, "Oleg", new BigDecimal("100.00"), Collections.singletonList(testOrder));

        Order secondTestOrder = new Order(2, new Certificate(2, "Jump park", "Free jumps at trampolines",
                new BigDecimal("35.00"), (short) 30, LocalDateTime.parse("2022-03-15T21:30"),
                LocalDateTime.parse("2022-06-15T21:30"), new HashSet<>(Arrays.asList(secondTestTag, thirdTestTag))),
                new BigDecimal("30.00"), LocalDateTime.parse("2022-04-20T15:12:34"));
        User secondTestUser = new User(2, "Ivan", new BigDecimal("75.78"), Collections.singletonList(secondTestOrder));

        OrderDto testOrderDto = new OrderDto(1,  new CertificateDto(1, "TattooLand", "The certificate allows to you make a tattoo",
                new BigDecimal("125.00"), (short) 92, LocalDateTime.parse("2022-01-20T21:00"),
                LocalDateTime.parse("2022-04-20T21:00"), Collections.singletonList(testTagDto)),
                new BigDecimal("125.00"), LocalDateTime.parse("2022-03-20T17:14:42"));
        testUserDto = new UserDto(1, "Oleg", new BigDecimal("100.00"), Collections.singletonList(testOrderDto));

        OrderDto secondTestOrderDto = new OrderDto(2, new CertificateDto(2, "Jump park", "Free jumps at trampolines",
                new BigDecimal("35.00"), (short) 30, LocalDateTime.parse("2022-03-15T21:30"),
                LocalDateTime.parse("2022-06-15T21:30"), Arrays.asList(secondTestTagDto, thirdTestTagDto)),
                new BigDecimal("30.00"), LocalDateTime.parse("2022-04-20T15:12:34"));
        UserDto secondTestUserDto = new UserDto(2, "Ivan", new BigDecimal("75.78"), Collections.singletonList(secondTestOrderDto));

        testUserList = Arrays.asList(testUser, secondTestUser);
        testUserDtoList = Arrays.asList(testUserDto, secondTestUserDto);
    }

    @Test
    void findUserByIdTest() {
        when(userService.findUserById(1)).thenReturn(testUser);
        when(userConverter.convert(testUser)).thenReturn(testUserDto);
        Assertions.assertEquals(testUserDto, userFacade.findUserById(1));
    }

    @Test
    void findAllUsersTest() {
        Map<String, String> pageParameters = new HashMap<>();
        pageParameters.put(MJCPaginationConstant.PAGE_PARAMETER, "1");
        pageParameters.put(MJCPaginationConstant.PAGE_SIZE_PARAMETER, "2");

        when(userService.findAllUsers(pageParameters)).thenReturn(testUserList);
        when(userConverter.convertAll(testUserList)).thenReturn(testUserDtoList);
    }

    @Test
    void findUserByOrderIdTest() {
        when(userService.findUserByOrderId(1)).thenReturn(testUser);
        when(userConverter.convert(testUser)).thenReturn(testUserDto);
        Assertions.assertEquals(testUserDto, userFacade.findUserByOrderId(1));
    }
}