package com.epam.esm.facade;

import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;

import java.util.List;
import java.util.Map;

public interface MJCUserFacade {
    UserDto findUserById(long id);

    List<UserDto> findAllUsers(Map<String, String> paginationParameters);
}
