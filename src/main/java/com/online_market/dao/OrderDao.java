package com.online_market.dao;

import com.online_market.entity.Order;
import com.online_market.entity.User;

import java.util.List;

/**
 * Dao interface for ${@link Order}
 *
 * @author Siarhei
 * @version 1.0
 */
public interface OrderDao {

    void save(Order order);

    void update(Order order);

    Order getById(int id);

    List<Order> userOrderList(int userId);

    List<Order> getAllOrders();


}
