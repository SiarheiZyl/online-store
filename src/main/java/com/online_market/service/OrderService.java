package com.online_market.service;

import com.online_market.entity.Item;
import com.online_market.entity.Order;
import com.online_market.entity.User;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Service interface for ${@link Order}
 *
 * @author Siarhei
 * @version 1.0
 */
public interface OrderService {

    void save(Order order);

    void update(Order order);

    Order getById(int id);

    List<Order> userOrderList(int userId);

    Order getBucketOrder(int userId);

    void saveBucketToOrders(Order order, int userId);

    void addToBucket(int itemId, int userId);

    void addNewItemToBucket(int itemId);

    void removeFromBucket(int itemId, int userId, int quantity);

    List<Order> getAllTrackedOrders();

    List<Order> getAllTrackedOrdersById(int userId);

    long sizeOfTrackedOrders();

    long sizeOfTrackedOrdersFilteredByDate(Date from, Date to);

    long sizeOfHistoryOfOrdersFilteredByDate(int userId, Date fromDate, Date toDate);

    List<Order> getOrdersPerPage(int pageId, int total);

    List<Order> getOrdersPerPageFilteredFromToDate(int pageId, int pageSize, Date fromDate, Date toDate);

    void updateQuantity(int userId, int itemId, int quantity);

    void setQuantity(int orderId, int itemId, int quantity);

    Map<Order, Map<Item, Integer>> getHistoryOfOrders(int userId);

    Map<Order, Map<Item, Integer>> getHistoryOfOrdersPerPage(int userId, int pageId, int pageSize);

    Map<Order, Map<Item, Integer>> getHistoryOfOrdersPerPageFilteredFromToDate(int userId, int pageId, int pageSize, Date fromDate, Date toDate);

    void repeatOrder(Order repeatedOrder, int orderId);

    void addItemToSession(int itemId, HttpSession session);

    void addFromSessionToBucket(Map<Item, Integer> itemMap, int userId);

    Map<User, Double> getTopUsers();

    Map<Item, Integer> getTopItems();

    Map<String, Double> getIncome();

    void sendUpdateMessageToJms();

    void updateTopItems();

    void updateBucket(int userId);
}
