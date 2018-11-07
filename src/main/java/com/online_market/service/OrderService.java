package com.online_market.service;

import com.online_market.entity.Item;
import com.online_market.entity.Order;
import com.online_market.entity.User;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface OrderService {

    void save(Order order);

    void update(Order order);

    Order getById(int id);

    List<Order> userOrderList(int userId);

    Order getBucketOrder(int userId);

    void saveBucketToOrders(Order order, int userId);

    void addToBucket(int itemId, int userId);

    void removeFromBucket(int itemId, int userId, int quantity);

    List<Order> getAllTrackedOrders();

    List<Order> getAllTrackedOrdersById(int userId);

    void updateQuantity(int userId, int itemId, int quantity);

    void setQuantity(int orderId, int itemId, int quantity);

    Map <Order, Map<Item, Integer>> getHistoryOfOrders(int userId);

    void repeatOrder(Order repeatedOrder, int orderId);

    void addItemToSession(int itemId, HttpSession session);

    void addFromSessionToBucket(Map <Item, Integer> itemMap, int userId);

    Map<User, Double> getTopUsers();

    Map<Item, Integer> getTopItems();

    Map<String, Double> getIncome();
}
