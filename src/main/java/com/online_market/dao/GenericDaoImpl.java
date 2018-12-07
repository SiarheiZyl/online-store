package com.online_market.dao;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Class implementing ${@link GenericDao<T>}
 *
 * @author Siarhei
 * @version 1.0
 */
@Repository
public abstract class GenericDaoImpl<T> implements GenericDao<T> {

    /**
     * SessionFactory exemplar through which we get
     * sessions and perform database operations
     */
    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Saving to DB
     * @param t exemplar of any entity
     */
    @Override
    public void save(T t) {
        sessionFactory.getCurrentSession().persist(t);
    }

    /**
     * Updating in DB
     * @param t exemplar of any entity
     */
    @Override
    public void update(T t) {
        sessionFactory.getCurrentSession().update(t);
    }

    /**
     * Saving to DB or updating
     * @param t exemplar of any entity
     */
    @Override
    public void saveOrUpdate(T t) {
        sessionFactory.getCurrentSession().saveOrUpdate(t);
    }

    /**
     * Getting entity by id
     * @param type type
     * @param id id
     * @return exemplar of provided type or null
     */
    @Override
    public T getById(final Class<T> type, int id) {

        Session session = sessionFactory.openSession();
        T t = session.get(type, id);
        Hibernate.initialize(t);
        session.close();

        return t;
    }

    /**
     * Getting all
     * @param type name of Entity
     * @return list of values
     */
    @Override
    public List<T> getAll(String type) {

        String s = "select e from " + type + " e";
        Query query = sessionFactory.getCurrentSession().createQuery(s);

        return query.getResultList();
    }
}
