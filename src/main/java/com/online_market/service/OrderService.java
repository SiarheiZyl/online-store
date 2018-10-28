package com.online_market.service;

import com.online_market.entity.Order;

import java.util.List;

public interface OrderService {

    void save(Order order);

    void update(Order order);

    public Order getById(int id);

    List<Order> userOrderList(int userId);

}
