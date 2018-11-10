package com.online_market.service;

import com.online_market.entity.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    void save(User user) throws Exception;

    User getById(int id);

    void update(User user);

    void delete(int id);

    User validate(String username, String password);

    void authorize(int id);

    void register(User user);

    void logout();

    int getAuthirizedUserId();

}
