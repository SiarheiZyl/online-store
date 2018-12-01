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
 *
 * @author Siarhei
 * @version 1.0
 */
@Repository
public class UserDaoImpl extends GenericDaoImpl<User> implements UserDao {

    final static Logger logger = Logger.getLogger(UserDao.class);

    private final SessionFactory sessionFactory;

    /**
     * Injecting constructor for SessionFactory exemplar
     */
    @Autowired
    public UserDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Validating user
     *
     * @param username username
     * @param password password
     * @return null if there is no user in DB otherwise ${@link User}
     */
    @Override
    public User validate(String username, String password) {

        String s = "select u from User u where login = :login and password = :password";
        Query query = sessionFactory.getCurrentSession().createQuery(s);
        query.setParameter("login", username);
        query.setParameter("password", password);
        List list = query.list();

        return list.size() > 0 ? (User) list.get(0) : null;
    }

    /**
     * Registration of user
     *
     * @param user user
     */
    @Override
    public void register(User user) {

        user.setAuth(true);
        save(user);
    }

    /**
     * Getting id of authorized user
     *
     * @return
     */
    @Override
    public int getAuthirizedUserId() {

        String s = "select e from User e where e.isAuth = true ";
        Query query = sessionFactory.getCurrentSession().createQuery(s);
        List<User> list = query.list();

        return list.size() > 0 ? list.get(0).getId() : 0;
    }
}
