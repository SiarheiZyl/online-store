package com.online_market.dao;

import com.online_market.entity.User;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Class implementing ${@link UserDao}
 * @author Siarhei
 * @version 1.0
 */
@Repository
public class UserDaoImpl implements UserDao {

    final static Logger logger = Logger.getLogger(UserDao.class);

    @Autowired
    SessionFactory sessionFactory;

    /**
     * Saving user
     * @param user user
     */
    @Override
    public void save(User user) {
        sessionFactory.getCurrentSession().persist(user);
    }

    /**
     * Getting user by id
     * @param id user id
     * @return user ${@link User}
     */
    @Override
    public User getById(int id) {

         Session session = sessionFactory.openSession();
         User user = session.get(User.class, id);
         Hibernate.initialize(user);
         session.close();

         return user;
    }

    /**
     * Getting all users
     * @return list of ${@link User}
     */
    @Override
    public List<User> findAll() {

        String s = "select e from User e";
        Query query = sessionFactory.getCurrentSession().createQuery(s);

        return query.getResultList();

    }

    /**
     * Updating user
     * @param user user
     */
    @Override
    public void update(User user) {
        sessionFactory.getCurrentSession().update(user);
    }

    /**
     * Validating user
     * @param username username
     * @param password password
     * @return null if there is no user in DB otherwise ${@link User}
     */
    @Override
    public User validate(String username, String password) {

        String s = "select * from users where login = :login and password = :password";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(s).addEntity(User.class);
        query.setParameter("login", username);
        query.setParameter("password", password);
        List list = query.list();

        return list.size()>0 ? (User)list.get(0) : null;
    }

    /**
     * Registration of user
     * @param user user
     */
    @Override
    public void register(User user) {

        user.setAuth(true);
        save(user);
    }

    /**
     * Getting id of authorized user
     * @return
     */
    @Override
    public int getAuthirizedUserId() {

        String s = "select e from User e where e.isAuth = true ";
        Query query = sessionFactory.getCurrentSession().createQuery(s);
        List<User> list  = query.list();

        return list.size()>0 ? list.get(0).getId() : 0;
    }
}
