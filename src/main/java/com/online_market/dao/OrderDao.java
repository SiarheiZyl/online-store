package com.online_market.dao;

import com.online_market.entity.Order;

import java.util.Date;
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

    long sizeOfTrackedOrdersFilteredByDate(Date from, Date to);

    List<Order> getOrdersPerPage(int pageId, int total);

    List<Order> getOrdersPerPageFilteredFromToDate(int pageId, int pageSize, Date fromDate, Date toDate);

}
