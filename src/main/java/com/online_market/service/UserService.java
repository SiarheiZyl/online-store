package com.online_market.service;

import com.online_market.entity.User;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

/**
 * Service interface for ${@link User}
 *
 * @author Siarhei
 * @version 1.0
 */
public interface UserService {

    List<User> findAll();

    User getById(int id);

    void update(User user);

    User validate(String username, String password);

    void authorize(int id);

    void register(User user);

    void logout();

    int getAuthorizedUserId();

    void setPasswordHash(User user);

    boolean isLoginUnique(User user);

}
