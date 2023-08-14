package com.epam.esm.service.impl;

import com.epam.esm.dao.MJCOrderDao;
import com.epam.esm.dao.MJCTagDao;
import com.epam.esm.dao.MJCUserDao;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.service.MJCUserService;
import com.epam.esm.service.constant.MJCPaginationConstant;
import com.epam.esm.service.exception.MJCUnknownEntityException;
import com.epam.esm.service.handler.MJCPaginationParametersHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MJCUserServiceImpl implements MJCUserService {
    private static final String NONEXISTENT_USER_MESSAGE = "nonexistent.user";
    private final MJCUserDao userDao;
    private final MJCOrderDao orderDao;
    private final MJCTagDao tagDao;
    private final MJCPaginationParametersHandler paginationParametersHandler;

    @Autowired
    public MJCUserServiceImpl(MJCUserDao userDao, MJCOrderDao orderDao, MJCTagDao tagDao, MJCPaginationParametersHandler paginationParametersHandler) {
        this.userDao = userDao;
        this.orderDao = orderDao;
        this.tagDao = tagDao;
        this.paginationParametersHandler = paginationParametersHandler;
    }

    @Override
    public User findUserById(long id) {
        Optional<User> userOptional = userDao.findById(id);
        if (!userOptional.isPresent()) {
            throw new MJCUnknownEntityException(Order.class, NONEXISTENT_USER_MESSAGE);
        }

        User user = userOptional.get();
        addOrdersToUserIfNone(user);
        return user;
    }

    @Override
    public List<User> findAllUsers(Map<String, String> paginationParameters) {
        Map<String, Integer> handledPaginationParameters = paginationParametersHandler.handlePaginationParameters(paginationParameters);
        List<User> users = userDao.findAll(handledPaginationParameters.get(MJCPaginationConstant.PAGE_PARAMETER),
                handledPaginationParameters.get(MJCPaginationConstant.PAGE_SIZE_PARAMETER));
        users.forEach(this::addOrdersToUserIfNone);

        return users;
    }

    @Override
    public User findUserByOrderId(long orderId) {
        Optional<User> userOptional = userDao.findByOrderId(orderId);
        if (!userOptional.isPresent()) {
            throw new MJCUnknownEntityException(Order.class, NONEXISTENT_USER_MESSAGE);
        }

        User user = userOptional.get();
        addOrdersToUserIfNone(user);
        return user;
    }

    private void addOrdersToUserIfNone(User user) {
        if (user.getOrders().isEmpty()) {
            List<Order> userOrders = orderDao.findOrdersByUserId(user.getId());
            userOrders.forEach(this::addTagsToUserOrderCertificateIfNone);
            user.setOrders(userOrders);
        }
    }

    private void addTagsToUserOrderCertificateIfNone(Order order) {
        if (order.getCertificate().getTags().isEmpty()) {
            order.getCertificate().setTags(new HashSet<>(tagDao.findTagsByCertificateId(order.getCertificate().getId())));
        }
    }
}
