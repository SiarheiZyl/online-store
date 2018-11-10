package com.online_market.service;

import com.online_market.dao.ItemDao;
import com.online_market.dao.UserDao;
import com.online_market.entity.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Class implementing ${@link ParamService}
 * @author Siarhei
 * @version 1.0
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    final static Logger logger = Logger.getLogger(UserService.class);

    @Autowired
    public UserDao userDao;

    @Autowired
    ItemDao itemDao;

    /**
     * Getting all users
     * @return list of ${@link User}
     */
    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    /**
     * Saving user
     * @param user user
     */
    @Override
    public void save(User user)  {

        logger.info("Saving user(called save(User user))");

        userDao.save(user);

        logger.info("User was saved");
    }

    /**
     * Getting user by id
     * @param id id
     * @return user
     */
    @Override
    public User getById(int id) {

        logger.info("Getting user by id(called getById(int id))");

        return userDao.getById(id);
    }

    /**
     * Updating user
     * @param user user
     */
    @Override
    public void update(User user) {

        logger.info("Updating user(called update(User user))");

        User user_for_update = getById(user.getId());

        if(user.getAddress() == null)
            user.setAddress(user_for_update.getAddress());

        if(user.getPassword().equals(""))
            user.setPassword(user_for_update.getPassword());
        if(user.getRole() == null)
            user.setRole(user_for_update.getRole());

        userDao.update(user);

        logger.info("User was updated");
    }

    /**
     * Validating user
     * @param username username
     * @param password password
     * @return null if user not found otherwise founded user
     */
    @Override
    public User validate(String username, String password) {

        logger.info("Validating user(called validate(String username, String password))");

        return userDao.validate(username, password);
    }

    /**
     * Register user
     * @param user user
     */
    @Override
    public void register(User user) {

        logger.info("Registration a new user(called register(User user))");

        userDao.register(user);
    }

    /**
     * Logout for all users
     */
    @Override
    public void logout() {

        logger.info("Logout");

        List<User> users = findAll();
        for (User user : users) {
            if(user.isAuth()){
                user.setAuth(false);
                update(user);
            }
        }
    }

    /**
     * Authorize user
     * @param id id
     */
    @Override
    public void authorize(int id) {

        logger.info("Authorizing(called authorize(int id))");

        User user = getById(id);
        user.setAuth(true);
        update(user);
    }

    /**
     * Getting authorized user id
     * @return user id
     */
    @Override
    public int getAuthorizedUserId() {

        logger.info("Getting authirized user id(called getAuthorizedUserId())");

        return userDao.getAuthirizedUserId();
    }
}
