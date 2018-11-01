package com.online_market.service;

import com.online_market.dao.ItemDao;
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
    ItemDao itemDao;

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
 /*       Order order1 = getById(order.getOrderId());
        order.setUser(order1.getUser());*/
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

        Order userBucket = new Order();
        List<Item> items = itemDao.itemList();
        userBucket.setItems(items);
        userBucket.setUser(userService.getById(userId));
        update(userBucket);

        return userBucket;
    }

    @Override
    public void saveBucketToOrders(Order order, int userId) {
        Order order1 = getBucketOrder(userId);

        order1.setDeliveryMethod(order.getDeliveryMethod());
        order1.setPaymentMethod(order.getPaymentMethod());

        update(order1);
    }

    @Override
    public void addToBucket(Item it, int userId) {
        Item item = itemDao.getById(it.getItemId());

        if (item.getAvailableCount()>0) {

        Order userBucket = getBucketOrder(userId);
            List<Item> itemList = userBucket.getItems()!=null ? userBucket.getItems() : new ArrayList<>();

            int quantity = 0;

           // boolean contains = true;

/*            for (Item item1 : itemList) {
                if(item1.getItemId()==item.getItemId()) {
                    contains = true;
                    break;
                }
            }*/


          /*  if(!contains) {
                quantity = 1;
                itemList.add(item);
            }*/
           /* else{*/
                for (Item item1 :itemList) {
                    if(item1.getItemId() == item.getItemId()){
                        item1.setAvailableCount(item1.getAvailableCount()-1);
                    }
                }
            //}
            item.setAvailableCount(item.getAvailableCount()-1);

            itemDao.update(item);


            userBucket.setUser(userService.getById(userId));

            userBucket.setItems(itemList);

            update(userBucket);

            //if(contains) {
                quantity = itemDao.orderedItemQuantity(userBucket.getOrderId(), item.getItemId()) + 1;
            //}

            updateQuantity(userId, item.getItemId(), quantity);

        }
    }

    @Override
    public void updateQuantity(int userId, int itemId, int quantity) {
        itemDao.updateQuantityOfOrderedItem(getBucketOrder(userId).getOrderId(), itemId, quantity);
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

    @Override
    public List<Order> getAllTrackedOrders() {
        List<Order> orders = orderDao.getAllOrders();

        for (Order order : orderDao.getAllOrders()) {
            if(order.getDeliveryMethod()==null && order.getPaymentMethod()==null)
                orders.remove(order);
        }

        return orders;
    }
}
