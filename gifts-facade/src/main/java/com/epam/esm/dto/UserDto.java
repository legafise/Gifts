package com.epam.esm.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class UserDto {
    private long id;
    private String login;
    private BigDecimal balance;
    private List<OrderDto> orders;

    public UserDto(long id, String login, BigDecimal balance, List<OrderDto> orders) {
        this.id = id;
        this.login = login;
        this.balance = balance;
        this.orders = orders;
    }

    public UserDto() {
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return id == userDto.id && Objects.equals(login, userDto.login) && Objects.equals(balance, userDto.balance) && Objects.equals(orders, userDto.orders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, balance, orders);
    }
}
