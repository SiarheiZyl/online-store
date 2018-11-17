package com.online_market.dao;

import com.online_market.entity.Order;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Class implementing ${@link OrderDao}
 *
 * @author Siarhei
 * @version 1.0
 */
@Repository
public class OrderDaoImpl extends GenericDaoImpl<Order> implements OrderDao {

    final static Logger logger = Logger.getLogger(OrderDao.class);

    @Autowired
    SessionFactory sessionFactory;

    /**
     * Getting all orders of user
     *
     * @param userId user id
     * @return list of ${@link Order}
     */
    @Override
    public List<Order> userOrderList(int userId) {

        String s = "select * from orders where ordering_user = :ordering_user";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(s).addEntity(Order.class);
        query.setParameter("ordering_user", userId);
        List list = query.list();

        return list;
    }

    /**
     * Size of list of tracked orders
     *
     * @return size
     */
    @Override
    public long sizeOfTrackedOrders() {

        String countQ = "Select count (f.id) from Order f where f.paymentMethod is not null and f.deliveryMethod is not null";
        Query countQuery = sessionFactory.getCurrentSession().createQuery(countQ);
        Long countResults = (Long) countQuery.uniqueResult();

        return countResults;
    }

    /**
     * Size of list of tracked orders filtered by date
     *
     * @param from from date
     * @param to to date
     * @return size
     */
    public long sizeOfTrackedOrdersFilteredByDate(Date from, Date to){

        java.sql.Date fromDate = new java.sql.Date(from.getTime());
        java.sql.Date toDate = new java.sql.Date(to.getTime());

        Query countQuery = sessionFactory.getCurrentSession().createQuery("Select count (f.id) from Order f where f.paymentMethod is not null and f.deliveryMethod is not null and f.date BETWEEN :stDate AND :edDate").setParameter("stDate", fromDate).setParameter("edDate", toDate);
        Long countResults = (Long) countQuery.uniqueResult();

        return countResults;
    }

    /**
     * Getting orders for pagination
     *
     * @param pageId pageId
     * @param total  total
     * @return list of ${@link Order}
     */
    @Override
    public List<Order> getOrdersPerPage(int pageId, int total) {

        Long countResults = sizeOfTrackedOrders();
        int lastPageNumber = (int) (Math.ceil(countResults / total)) + 1;

        Query selectQuery = sessionFactory.getCurrentSession().createQuery("From Order as order where order.paymentMethod is not null and order.deliveryMethod is not null");

        if (lastPageNumber == pageId) {
            selectQuery.setFirstResult(1);
            selectQuery.setMaxResults((int) (countResults - (lastPageNumber - 1) * total));
        } else {

            selectQuery.setFirstResult((int) (countResults - (pageId) * total));
            selectQuery.setMaxResults(total);
        }
        List<Order> list = selectQuery.list();
        Collections.reverse(list);

        return list;
    }


    /**
     * Getting orders for pagination filtered by date
     *
     * @param pageId page id
     * @param pageSize page size
     * @param fromDate from
     * @param toDate to
     * @return @return list of ${@link Order}
     */
    @Override
    public List<Order> getOrdersPerPageFilteredFromToDate(int pageId, int pageSize, Date fromDate, Date toDate) {

        java.sql.Date from = new java.sql.Date(fromDate.getTime());
        java.sql.Date to = new java.sql.Date(toDate.getTime());

        Query selectQuery = sessionFactory.getCurrentSession().createQuery("From Order as order where order.paymentMethod is not null and order.deliveryMethod is not null and order.date BETWEEN :stDate AND :edDate").setParameter("stDate", from).setParameter("edDate", to);

        selectQuery.setFirstResult((pageId-1)*pageSize);
        selectQuery.setMaxResults(pageSize);

        List<Order> list = selectQuery.list();


        return list;
    }
}
