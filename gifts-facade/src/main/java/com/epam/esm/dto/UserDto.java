package com.epam.esm.dto;

import com.epam.esm.entity.Order;

import java.math.BigDecimal;
import java.util.List;

public class UserDto {
    private long id;
    private String login;
    private BigDecimal balance;
    private List<OrderDto> orders;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public List<OrderDto> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDto> orders) {
        this.orders = orders;
    }
}
