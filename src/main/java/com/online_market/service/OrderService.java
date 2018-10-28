package com.online_market.service;

import com.online_market.entity.Item;
import com.online_market.entity.Order;

import java.util.List;

public interface OrderService {

    void save(Order order);

    void update(Order order);

    Order getById(int id);

    List<Order> userOrderList(int userId);

    Order getBucketOrder(int userId);

    void saveBucketToOrders(Order order, int userId);

    void addToBucket(Item item, int userId);

    void removeFromBucket(int itemId, int userId);
}
