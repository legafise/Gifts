package com.epam.esm.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public final class DataSource {
    private static final String DB_PROPERTIES_PATH = "gifts-dao/src/main/resources/db_config.properties";
    private static final String URL_PROPERTY = "url";
    private static final String USER_PROPERTY = "user";
    private static final String PASSWORD_PROPERTY = "password";
    private static final String DRIVER_NAME_PROPERTY = "driverName";
    private static final HikariConfig CONFIG;
    private static final HikariDataSource DATA_SOURCE;

    static {
        try {
            CONFIG = new HikariConfig();
            Properties properties = new Properties();
            properties.load(new FileInputStream(DB_PROPERTIES_PATH));

            CONFIG.setDriverClassName(properties.getProperty(DRIVER_NAME_PROPERTY));
            CONFIG.setJdbcUrl(properties.getProperty(URL_PROPERTY));
            CONFIG.setUsername(properties.getProperty(USER_PROPERTY));
            CONFIG.setPassword(properties.getProperty(PASSWORD_PROPERTY));
            DATA_SOURCE = new HikariDataSource(CONFIG);
            DATA_SOURCE.setMinimumIdle(5);
        } catch (IOException e) {
            throw new DaoException(e);
        }
    }

    public static Connection getConnection() {
        try {
            return DATA_SOURCE.getConnection();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
