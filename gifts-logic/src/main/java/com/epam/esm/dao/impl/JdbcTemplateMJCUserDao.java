package com.epam.esm.dao.impl;

import com.epam.esm.dao.MJCUserDao;
import com.epam.esm.dao.mapper.MJCUserMapperImpl;
import com.epam.esm.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Profile({"template", "template-test"})
public class JdbcTemplateMJCUserDao implements MJCUserDao {
    private static final String ADD_ORDER_TO_USER_SQL = "INSERT INTO user_orders (user_id, order_id) VALUES (?, ?)";
    private static final String UPDATE_USER = "UPDATE users SET users.login = ?, users.balance = ? WHERE users.id = ?";
    private static final String FIND_ALL_USERS_SQL = "SELECT users.id AS user_id, users.login, users.balance FROM users ORDER BY users.id ASC LIMIT ? OFFSET ?";
    private static final String FIND_USER_BY_ID_SQL = "SELECT users.id AS user_id, users.login, users.balance FROM users WHERE users.id = ?";
    private static final String FIND_USER_BY_ORDER_ID_SQL = "SELECT users.id AS user_id, users.login, users.balance FROM" +
            " users INNER JOIN user_orders ON user_orders.user_id = users.id WHERE user_orders.order_id = ?";
    private final MJCUserMapperImpl userMapper;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTemplateMJCUserDao(MJCUserMapperImpl userMapper, JdbcTemplate jdbcTemplate) {
        this.userMapper = userMapper;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<User> findById(long id) {
        List<User> userList = jdbcTemplate.query(FIND_USER_BY_ID_SQL, userMapper, id);

        return userList.isEmpty() ? Optional.empty()
                : Optional.of(userList.get(0));
    }

    @Override
    public List<User> findAll(int page, int pageSize) {
        return jdbcTemplate.query(FIND_ALL_USERS_SQL, new Object[]{pageSize, (page - 1) * pageSize}, userMapper);
    }

    @Override
    public User update(User user) {
        jdbcTemplate.update(UPDATE_USER, user.getLogin(), user.getBalance(), user.getId());
        return jdbcTemplate.query(FIND_USER_BY_ID_SQL, userMapper, user.getId()).get(0);
    }

    @Override
    public boolean addOrderToUser(long userId, long orderId) {
        return jdbcTemplate.update(ADD_ORDER_TO_USER_SQL, userId, orderId) >= 1;
    }

    @Override
    public Optional<User> findByOrderId(long orderId) {
        List<User> userList = jdbcTemplate.query(FIND_USER_BY_ORDER_ID_SQL, userMapper, orderId);

        return userList.isEmpty() ? Optional.empty()
                : Optional.of(userList.get(0));
    }
}
