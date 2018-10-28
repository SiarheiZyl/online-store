package com.online_market.dao;

import com.online_market.entity.Order;
import com.online_market.entity.User;

import java.util.List;

public interface OrderDao {

    void save(Order order);

    void update(Order order);

    Order getById(int id);

    List<Order> userOrderList(int userId);

}
