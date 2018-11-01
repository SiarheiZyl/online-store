package com.online_market.service;

import com.online_market.dao.ItemDao;
import com.online_market.dao.UserDao;
import com.online_market.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    public UserDao userDao;

    @Autowired
    ItemDao itemDao;

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public void save(User user)  {

        userDao.save(user);
    }

    @Override
    public User getById(int id) {
        return userDao.getById(id);
    }

    @Override
    public void update(User user) {

        User user_for_update = getById(user.getId());

        if(user.getAddress() == null)
            user.setAddress(user_for_update.getAddress());

        if(user.getPassword().equals(""))
            user.setPassword(user_for_update.getPassword());
        if(user.getRole() == null)
            user.setRole(user_for_update.getRole());


        userDao.update(user);
    }

    @Override
    public void delete(int id) {
        userDao.delete(id);
    }

    @Override
    public User validate(String username, String password) {
        return userDao.validate(username, password);
    }

    @Override
    public void register(User user) {
        userDao.register(user);
    }

    @Override
    public void logout() {
        List<User> users = findAll();
        for (User user : users) {
            if(user.isAuth()){
                user.setAuth(false);
                update(user);
            }

        }

    }

    @Override
    public void authorize(int id) {
        User user = getById(id);
        user.setAuth(true);
        update(user);
    }

    @Override
    public void test() {
        System.out.println(itemDao.orderedItemId(56, 2));
        System.out.println(itemDao.orderedItemQuantity(56, 2));


    }
}
