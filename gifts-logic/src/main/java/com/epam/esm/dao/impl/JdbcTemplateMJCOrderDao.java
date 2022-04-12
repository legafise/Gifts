package com.epam.esm.dao.impl;

import com.epam.esm.dao.MJCOrderDao;
import com.epam.esm.dao.extractor.MJCOrderExtractorImpl;
import com.epam.esm.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile({"template", "template-test"})
public class JdbcTemplateMJCOrderDao implements MJCOrderDao {
    private static final String FIND_ORDER_BY_ID_SQL = "SELECT orders.id AS order_id, orders.price AS order_price, orders.purchase_time, gift_certificates.id AS certificate_id," +
            " gift_certificates.name AS gift_certificate_name, gift_certificates.description, gift_certificates.price AS certificate_price," +
            " gift_certificates.duration, gift_certificates.create_date, gift_certificates.is_deleted, gift_certificates.last_update_date FROM orders LEFT JOIN gift_certificates ON orders.certificate_id = gift_certificates.id WHERE orders.id = ?";
    private static final String FIND_ORDERS_BY_USER_ID_SQL = "SELECT orders.id AS order_id, orders.price AS order_price, orders.purchase_time, gift_certificates.id AS certificate_id," +
            " gift_certificates.name AS gift_certificate_name, gift_certificates.description, gift_certificates.price AS certificate_price," +
            " gift_certificates.duration, gift_certificates.create_date, gift_certificates.is_deleted," +
            " gift_certificates.last_update_date FROM user_orders INNER JOIN orders ON user_orders.order_id = orders.id LEFT JOIN" +
            " gift_certificates ON orders.certificate_id = gift_certificates.id WHERE user_orders.user_id = ?";
    private static final String ADD_ORDER_SQL = "INSERT INTO orders (certificate_id, price, purchase_time) VALUES (?, ?, ?)";
    private static final String FIND_MAX_ORDER_ID = "SELECT MAX(id) FROM orders";
    private final MJCOrderExtractorImpl orderExtractor;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTemplateMJCOrderDao(MJCOrderExtractorImpl orderExtractor, JdbcTemplate jdbcTemplate) {
        this.orderExtractor = orderExtractor;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Order> findById(long id) {
        List<Order> orderList = jdbcTemplate.query(FIND_ORDER_BY_ID_SQL, orderExtractor, id);

        return orderList == null || orderList.isEmpty() ? Optional.empty()
                : Optional.of(orderList.get(0));
    }

    @Override
    public void add(Order order) {
        jdbcTemplate.update(ADD_ORDER_SQL, order.getCertificate().getId(), order.getPrice(), order.getPurchaseTime());
    }

    @Override
    public long findMaxOrderId() {
        return jdbcTemplate.queryForObject(FIND_MAX_ORDER_ID, Long.class);
    }

    @Override
    public List<Order> findOrdersByUserId(long userId) {
        return jdbcTemplate.query(FIND_ORDERS_BY_USER_ID_SQL, orderExtractor, userId);
    }
}