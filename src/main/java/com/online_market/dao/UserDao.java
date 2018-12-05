package com.online_market.dao;

import com.online_market.entity.User;

import java.util.List;


/**
 * Dao interface for ${@link User}
 *
 * @author Siarhei
 * @version 1.0
 */
public interface UserDao extends GenericDao<User>{

    User validate(String username, String password);

    void register(User user);

    int getAuthirizedUserId();

    User getUserByLogin(String login);
}
