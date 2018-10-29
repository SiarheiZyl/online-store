package com.online_market.dao;

import com.online_market.entity.User;
import org.hibernate.*;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void save(User user) {
        sessionFactory.getCurrentSession().persist(user);
    }

    @Override
    public User getById(int id) {

         Session session = sessionFactory.openSession();
         User user = session.get(User.class, id);
         Hibernate.initialize(user);
         session.close();

         return user;
    }

    @Override
    public List<User> findAll() {

        String s = "select e from User e";
        Query query = sessionFactory.getCurrentSession().createQuery(s);

        return query.getResultList();

    }

    @Override
    public void update(User user) {
        sessionFactory.getCurrentSession().update(user);
    }

    @Override
    public void delete(int id) {

        String s = "delete from users where id = :id";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(s);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public User validate(String username, String password) {

        String s = "select * from users where login = :login and password = :password";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(s).addEntity(User.class);
        query.setParameter("login", username);
        query.setParameter("password", password);
        List list = query.list();

        return list.size()>0 ? (User)list.get(0) : null;
    }

    @Override
    public void register(User user) {
        save(user);
    }
}
