package com.epam.esm.dao.mapper;

import com.epam.esm.entity.User;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@Profile({"template", "template-test"})
public class MJCUserMapperImpl implements RowMapper<User> {
    private static final String USER_ID = "user_id";
    private static final String USER_LOGIN = "login";
    private static final String USER_BALANCE = "balance";

    @Override
    public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong(USER_ID));
        user.setLogin(resultSet.getString(USER_LOGIN));
        user.setBalance(resultSet.getBigDecimal(USER_BALANCE));

        return user;
    }
}
