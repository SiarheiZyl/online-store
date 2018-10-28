package com.online_market.service;

import com.online_market.dao.OrderDao;
import com.online_market.entity.Item;
import com.online_market.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDao orderDao;

    @Autowired
    UserService userService;

    @Override
    public void save(Order order) {
        orderDao.save(order);
    }

    @Override
    public Order getById(int id) {
      return orderDao.getById(id);
    }

    @Override
    public void update(Order order) {
        orderDao.update(order);
    }

    @Override
    public List<Order> userOrderList(int userId) {
        return orderDao.userOrderList(userId);
    }

    @Override
    public Order getBucketOrder(int userId) {
        List<Order> orders = userOrderList(userId);

        for (Order order : orders) {
            if(order.getDeliveryMethod()==null && order.getPaymentMethod()==null)
                return order;
        }
        return null;
    }

    @Override
    public void saveBucketToOrders(Order order, int userId) {
        Order order1 = getBucketOrder(userId);

        order1.setDeliveryMethod(order.getDeliveryMethod());
        order1.setPaymentMethod(order.getPaymentMethod());

        update(order1);
    }

    @Override
    public void addToBucket(Item item, int userId) {
        Order userBucket = getBucketOrder(userId);

        if(userBucket==null) {
            userBucket = new Order();

        }

        List<Item> itemList = userBucket.getItems()!=null ? new ArrayList<>(userBucket.getItems()) : new ArrayList<>();
        itemList.add(item);

        userBucket.setUser(userService.getById(userId));

        userBucket.setItems(itemList);

        update(userBucket);
    }

 //?
    @Override
    public void removeFromBucket(int itemId, int userId) {
        Order userBucket = getBucketOrder(userId);

        List<Item> items = userBucket.getItems();
        for (Item item : userBucket.getItems()) {
            if(item.getItemId()==itemId)
                items.remove(item);
        }

        userBucket.setItems(items);

        update(userBucket);
    }
}
