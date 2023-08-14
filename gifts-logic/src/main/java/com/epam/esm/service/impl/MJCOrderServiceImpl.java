package com.epam.esm.service.impl;

import com.epam.esm.dao.MJCOrderDao;
import com.epam.esm.dao.MJCTagDao;
import com.epam.esm.dao.MJCUserDao;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.service.MJCCertificateService;
import com.epam.esm.service.MJCOrderService;
import com.epam.esm.service.MJCUserService;
import com.epam.esm.service.exception.MJCNotEnoughMoneyException;
import com.epam.esm.service.exception.MJCUnknownEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class MJCOrderServiceImpl implements MJCOrderService {
    private static final String NONEXISTENT_ORDER_MESSAGE = "nonexistent.order";
    private static final String NOT_ENOUGH_MONEY_MESSAGE = "not.enough.money";
    private static final BigDecimal MIN_ALLOWABLE_BALANCE = new BigDecimal("0");
    private final MJCOrderDao orderDao;
    private final MJCUserDao userDao;
    private final MJCTagDao tagDao;
    private final MJCUserService userService;
    private final MJCCertificateService certificateService;

    @Autowired
    public MJCOrderServiceImpl(MJCOrderDao orderDao, MJCUserDao userDao, MJCTagDao tagDao, MJCUserService userService, MJCCertificateService certificateService) {
        this.orderDao = orderDao;
        this.userDao = userDao;
        this.tagDao = tagDao;
        this.userService = userService;
        this.certificateService = certificateService;
    }

    @Override
    public Order findOrderById(long orderId) {
        Optional<Order> orderOptional = orderDao.findById(orderId);
        if (!orderOptional.isPresent()) {
            throw new MJCUnknownEntityException(Order.class, NONEXISTENT_ORDER_MESSAGE);
        }

        Order order = orderOptional.get();
        addOrderCertificateTagsIfNone(order);

        return orderOptional.get();
    }

    @Override
    @Transactional
    public Order createOrder(long userId, long certificateId) {
        Certificate certificate = certificateService.findCertificateById(certificateId);
        User user = userService.findUserById(userId);
        Order order = new Order(certificate, certificate.getPrice(), LocalDateTime.now());
        BigDecimal residualBalance = user.getBalance().subtract(order.getPrice());

        if (residualBalance.compareTo(MIN_ALLOWABLE_BALANCE) < 0) {
            throw new MJCNotEnoughMoneyException(NOT_ENOUGH_MONEY_MESSAGE);
        }

        orderDao.add(order);
        long lastAddedOrderId = orderDao.findMaxOrderId();
        user.setBalance(residualBalance);
        userDao.update(user);
        userDao.addOrderToUser(user.getId(), lastAddedOrderId);

        return findOrderById(lastAddedOrderId);
    }

    @Override
    public List<Order> findOrdersByUserId(long userId) {
        List<Order> orders = orderDao.findOrdersByUserId(userId);
        orders.forEach(this::addOrderCertificateTagsIfNone);
        return orders;
    }


    private void addOrderCertificateTagsIfNone(Order order) {
        if (order.getCertificate().getTags().isEmpty()) {
            order.getCertificate().setTags(new HashSet<>(tagDao.findTagsByCertificateId(order.getCertificate().getId())));
        }
    }
}
