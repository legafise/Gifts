package com.epam.esm.dao.impl;

import com.epam.esm.dao.MJCOrderDao;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
@Profile({"prod", "hibernate-test"})
public class HibernateMJCOrderDao implements MJCOrderDao {
    private static final String FIND_MAX_ORDER_ID = "SELECT MAX(id) FROM orders";
    private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Order> findById(long id) {
        return Optional.ofNullable(entityManager.find(Order.class, id));
    }

    @Override
    public void add(Order order) {
        order.setId(0);
        entityManager.persist(order);
    }

    @Override
    public long findMaxOrderId() {
        BigInteger maxOrderId = (BigInteger) entityManager.createNativeQuery(FIND_MAX_ORDER_ID).getResultList().get(0);
        return maxOrderId.longValue();
    }

    @Override
    public List<Order> findOrdersByUserId(long userId) {
        return entityManager.find(User.class, userId).getOrders();
    }
}
