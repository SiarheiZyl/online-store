package com.online_market.dao;

import com.online_market.entity.Item;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Siarhei
 */
@Repository
public abstract class GenericDaoImpl<T> implements GenericDao<T> {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(T t) {
        sessionFactory.getCurrentSession().persist(t);
    }

    @Override
    public void update(T t) {
        sessionFactory.getCurrentSession().update(t);
    }

    @Override
    public void saveOrUpdate(T t) {
        sessionFactory.getCurrentSession().saveOrUpdate(t);
    }

    @Override
    public T getById(final Class<T> type, int id) {

        Session session = sessionFactory.openSession();
        T t = session.get(type, id);
        Hibernate.initialize(t);
        session.close();

        return t;
    }

    @Override
    public List<T> getAll(String type) {

        String s = "select e from " + type + " e";
        Query query = sessionFactory.getCurrentSession().createQuery(s);

        return query.getResultList();
    }
}
