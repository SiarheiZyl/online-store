package com.online_market.service;

import com.online_market.dao.UserDao;
import com.online_market.entity.User;
import com.online_market.utils.HashPasswordUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Class implementing ${@link ParamService}
 *
 * @author Siarhei
 * @version 1.0
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    /**
     * Apache log4j object is used to log all important info
     */
    final static Logger logger = Logger.getLogger(UserService.class);

    /**
     * User dao bean
     */
    private final UserDao userDao;

    /**
     * Injecting constructor
     *
     * @param userDao user DAO
     */
    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Getting all users
     *
     * @return list of ${@link User}
     */
    @Override
    public List<User> findAll() {
        return userDao.getAll("User");
    }

    /**
     * Getting user by id
     *
     * @param id id
     * @return user
     */
    @Override
    public User getById(int id) {

        logger.info("Getting user by id(called getById(int id))");

        return userDao.getById(User.class, id);
    }

    /**
     * Updating user
     *
     * @param user user
     */
    @Override
    public void update(User user) {

        logger.info("Updating user(called saveOrUpdate(User user))");

        User user_for_update = getById(user.getId());
        if (user.getFirstName() == null || user.getFirstName().equals(""))
            user.setFirstName(user_for_update.getFirstName());
        if (user.getLastName() == null || user.getLastName().equals(""))
            user.setLastName(user_for_update.getLastName());
        if (user.getLogin() == null || user.getLogin().equals(""))
            user.setLogin(user_for_update.getLogin());
        if (user.getEmail() == null || user.getEmail().equals(""))
            user.setEmail(user_for_update.getEmail());
        if (user.getBirthdate() == null)
            user.setBirthdate(user_for_update.getBirthdate());
        if (user.getAddress() == null)
            user.setAddress(user_for_update.getAddress());
        if (user.getPassword() == null || user.getPassword().equals(""))
            user.setPassword(user_for_update.getPassword());
        if (user.getRole() == null)
            user.setRole(user_for_update.getRole());

        userDao.update(user);

        logger.info("User was updated");
    }

    /**
     * Validating user
     *
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
     *
     * @param user user
     */
    @Override
    public void register(User user) {

        logger.info("Registration a new user(called register(User user))");

        setPasswordHash(user);

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
            if (user.isAuth()) {
                user.setAuth(false);
                userDao.update(user);
            }
        }
    }

    /**
     * Authorize user
     *
     * @param id id
     */
    @Override
    public void authorize(int id) {

        logger.info("Authorizing(called authorize(int id))");

        User user = getById(id);
        user.setAuth(true);
        userDao.update(user);
    }

    /**
     * Getting authorized user id
     *
     * @return user id
     */
    @Override
    public int getAuthorizedUserId() {

        logger.info("Getting authirized user id(called getAuthorizedUserId())");

        return userDao.getAuthirizedUserId();
    }


    /**
     * Setting password hash
     *
     * @param user user
     */
    @Override
    public void setPasswordHash(User user) {
        try {
            user.setPassword(HashPasswordUtil.getSaltedHash(user.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method for chech if login is unique
     *
     * @param user user
     * @return true if login is unique
     */
    @Override
    public boolean isLoginUnique(User user) {

        User user1 = userDao.getUserByLogin(user.getLogin());

        return user1 == null;
    }
}
