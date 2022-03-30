package com.epam.esm.populator;

import com.epam.esm.converter.MJCConverter;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class MJCUserPopulator implements MJCPopulator<User, UserDto> {
    @Autowired
    @Qualifier("mjcOrderConverter")
    private MJCConverter<Order, OrderDto> orderConverter;

    public void setOrderConverter(MJCConverter<Order, OrderDto> orderConverter) {
        this.orderConverter = orderConverter;
    }

    @Override
    public void populate(User user, UserDto userDto) {
        userDto.setId(user.getId());
        userDto.setBalance(user.getBalance());
        userDto.setLogin(user.getLogin());
        userDto.setOrders(orderConverter.convertAll(user.getOrders()));
    }
}
