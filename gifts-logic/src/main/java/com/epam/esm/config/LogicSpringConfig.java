package com.epam.esm.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.epam.esm")
@PropertySource("classpath:db_config.properties")
public class LogicSpringConfig {
    private static final String URL_PROPERTY = "url";
    private static final String USER_PROPERTY = "user";
    private static final String PASSWORD_PROPERTY = "password";
    private static final String DRIVER_NAME_PROPERTY = "driverName";

    @Autowired
    private Environment environment;

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public DataSource dataSource() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDriverClassName(environment.getProperty(DRIVER_NAME_PROPERTY));
        hikariDataSource.setJdbcUrl(environment.getProperty(URL_PROPERTY));
        hikariDataSource.setUsername(environment.getProperty(USER_PROPERTY));
        hikariDataSource.setPassword(environment.getProperty(PASSWORD_PROPERTY));
        hikariDataSource.setMinimumIdle(5);

        return hikariDataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
