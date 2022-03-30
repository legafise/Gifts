package com.epam.esm.service.impl;

import com.epam.esm.dao.MJCUserDao;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.service.MJCUserService;
import com.epam.esm.service.constant.MJCPaginationConstant;
import com.epam.esm.service.exception.MJCUnknownEntityException;
import com.epam.esm.service.handler.MJCPaginationParametersHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MJCUserServiceImpl implements MJCUserService {
    private static final String NONEXISTENT_USER_MESSAGE = "nonexistent.user";
    private final MJCUserDao userDao;
    private final MJCPaginationParametersHandler paginationParametersHandler;

    @Autowired
    public MJCUserServiceImpl(MJCUserDao userDao, MJCPaginationParametersHandler paginationParametersHandler) {
        this.userDao = userDao;
        this.paginationParametersHandler = paginationParametersHandler;
    }

    @Override
    public User findUserById(long id) {
        Optional<User> user = userDao.findById(id);
        if (!user.isPresent()) {
            throw new MJCUnknownEntityException(Order.class, NONEXISTENT_USER_MESSAGE);
        }

        return user.get();
    }

    @Override
    public List<User> findAllUsers(Map<String, String> paginationParameters) {
        Map<String, Integer> handledPaginationParameters = paginationParametersHandler.handlePaginationParameters(paginationParameters);

        return userDao.findAll(handledPaginationParameters.get(MJCPaginationConstant.PAGE_PARAMETER),
                handledPaginationParameters.get(MJCPaginationConstant.PAGE_SIZE_PARAMETER));
    }
}
