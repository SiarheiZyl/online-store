package com.online_market.dao;

import com.online_market.entity.Order;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class OrderDaoImpl implements OrderDao {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void save(Order order) {
        sessionFactory.getCurrentSession().persist(order);
    }

    @Override
    public void update(Order order) {
 /*       if(getById(order.getOrderId()) == null)
            save(order);
        else
            sessionFactory.getCurrentSession().update(order);*/
        sessionFactory.getCurrentSession().saveOrUpdate(order);
    }

    @Override
    public Order getById(int id) {

        Session session = sessionFactory.openSession();
        Order order = session.get(Order.class, id);
        Hibernate.initialize(order);
        session.close();

        return order;
    }


    @Override
    public List<Order> userOrderList(int userId) {

        String s = "select * from orders where ordering_user = :ordering_user";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(s).addEntity(Order.class);
        query.setParameter("ordering_user", userId);
        List list = query.list();

        return list;
    }

    @Override
    public List<Order> getAllOrders() {
        String s = "select e from Order e";
        Query query = sessionFactory.getCurrentSession().createQuery(s);

        return query.getResultList();
    }


}
