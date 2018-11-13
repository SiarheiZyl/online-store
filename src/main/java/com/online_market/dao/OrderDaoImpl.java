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
}
