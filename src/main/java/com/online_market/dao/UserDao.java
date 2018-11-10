package com.online_market.dao;

import com.online_market.entity.User;

import java.util.List;


/**
 * Dao interface for ${@link User}
 * @author Siarhei
 * @version 1.0
 */
public interface UserDao {

    void save(User user);

    User getById(int id);

    List<User> findAll();

    void update(User user);

    User validate(String username, String password);

    void register(User user);

    int getAuthirizedUserId();

}
