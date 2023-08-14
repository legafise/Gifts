package com.epam.esm.facade.impl;

import com.epam.esm.converter.MJCConverter;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.facade.MJCUserFacade;
import com.epam.esm.service.MJCUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class MJCUserFacadeImpl implements MJCUserFacade {
    @Autowired
    @Qualifier("mjcUserConverter")
    private MJCConverter<User, UserDto> userConvertor;
    @Autowired
    private MJCUserService userService;

    public void setUserConvertor(MJCConverter<User, UserDto> userConvertor) {
        this.userConvertor = userConvertor;
    }

    public void setUserService(MJCUserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDto findUserById(long id) {
        return userConvertor.convert(userService.findUserById(id));
    }

    @Override
    public List<UserDto> findAllUsers(Map<String, String> paginationParameters) {
        return userConvertor.convertAll(userService.findAllUsers(paginationParameters));
    }

    @Override
    public UserDto findUserByOrderId(long orderId) {
        return userConvertor.convert(userService.findUserByOrderId(orderId));
    }
}
