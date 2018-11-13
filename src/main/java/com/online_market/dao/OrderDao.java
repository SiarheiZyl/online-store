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
public interface OrderDao extends GenericDao<Order> {

    List<Order> userOrderList(int userId);

    long sizeOfTrackedOrders();

    List<Order> getOrdersPerPage(int pageId, int total);



}
