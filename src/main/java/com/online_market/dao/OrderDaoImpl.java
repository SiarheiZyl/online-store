package com.online_market.dao;

import com.online_market.entity.Order;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Class implementing ${@link OrderDao}
 * @author Siarhei
 * @version 1.0
 */
@Repository
public class OrderDaoImpl implements OrderDao {

    final static Logger logger = Logger.getLogger(OrderDao.class);

    @Autowired
    SessionFactory sessionFactory;

    /**
     * Saving order
     * @param order order
     */
    @Override
    public void save(Order order) {
        sessionFactory.getCurrentSession().persist(order);
    }

    /**
     * Updating order
     * @param order order
     */
    @Override
    public void update(Order order) {
        sessionFactory.getCurrentSession().saveOrUpdate(order);
    }

    /**
     * Getting order by id
     * @param id id
     * @return order
     */
    @Override
    public Order getById(int id) {

        Session session = sessionFactory.openSession();
        Order order = session.get(Order.class, id);
        Hibernate.initialize(order);
        session.close();

        return order;
    }

    /**
     * Getting all orders of user
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
     * Getting all orders
     * @return list of ${@link Order}
     */
    @Override
    public List<Order> getAllOrders() {
        String s = "select e from Order e";
        Query query = sessionFactory.getCurrentSession().createQuery(s);

        return query.getResultList();
    }
}
