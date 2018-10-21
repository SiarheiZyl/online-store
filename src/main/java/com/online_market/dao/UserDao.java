package com.online_market.dao;

import com.online_market.entity.User;

import java.util.List;

public interface UserDao {

    void save(User user);

    User getById(int id);

    List<User> findAll();

    void update(User user);

    void delete(int id);

    User validate(String username, String password);

    void register(User user);

}
