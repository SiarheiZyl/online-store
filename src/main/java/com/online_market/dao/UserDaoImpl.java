package com.online_market.dao;

import com.online_market.entity.User;
import com.online_market.utils.HashPasswordUtil;
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

    /**
     * SessionFactory exemplar through which we get
     * sessions and perform database operations
     */
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

        User userForValidation = getUserByLogin(username);

        if (userForValidation == null)
            return null;

        try {
            if (HashPasswordUtil.check(password, userForValidation.getPassword()))
                return userForValidation;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
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

    /**
     * Getting user by login
     *
     * @param login login
     * @return null if no such login in DB otherwise {@link User}
     */
    @Override
    public User getUserByLogin(String login) {

        String s = "select u from User u where login = :login";
        Query query = sessionFactory.getCurrentSession().createQuery(s);
        query.setParameter("login", login);
        List list = query.list();

        return list.size() == 0 ? null : (User) list.get(0);
    }
}
