package com.online_market.service;

import com.online_market.dao.ItemDao;
import com.online_market.dao.OrderDao;
import com.online_market.entity.Item;
import com.online_market.entity.Order;
import com.online_market.entity.enums.OrderStatus;
import com.online_market.entity.enums.PaymentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDao orderDao;

    @Autowired
    ItemDao itemDao;

    @Autowired
    UserService userService;

    @Autowired
   ItemService itemService;

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
        java.util.Date date1 = new java.util.Date();
        order1.setDate(new Date(date1.getTime()));
        order1.setAmount(order.getAmount());

        update(order1);
    }

/*    @Override
    public void saveBucketToOrders(Order order, int userId) {
        Order order1 = getBucketOrder(userId);

        Map<Item, Integer> itemQuantity = itemDao.getNotNullItemsInBucket(order1.getOrderId());
        List<Item> itemList = new ArrayList<>(itemQuantity.keySet());

        order1.setDeliveryMethod(order.getDeliveryMethod());
        order1.setPaymentMethod(order.getPaymentMethod());
        order1.setItems(itemList);
        for (Item item : itemList) {
            setQuantity(order1.getOrderId(), item.getItemId(), itemQuantity.get(item));
        }
        update(order1);


    }*/

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

    @Override
    public void setQuantity(int orderId, int itemId, int quantity) {
        itemDao.updateQuantityOfOrderedItem(orderId, itemId, quantity);
    }

    //?
    @Override
    public void removeFromBucket(int itemId, int userId, int quantity) {
        Item item = itemDao.getById(itemId);
        item.setAvailableCount(item.getAvailableCount()+quantity);
        itemDao.update(item);
        updateQuantity(userId, itemId, 0);
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

    @Override
    public List<Order> getAllTrackedOrdersById(int userId) {
        List<Order> result = new ArrayList<>();

        for (Order order : getAllTrackedOrders()) {
            if(order.getUser().getId() == userId)
                result.add(order);
        }
        return result;
    }

    @Override
    public Map<Order, Map<Item, Integer>> getHistoryOfOrders(int userId) {
        Map<Order, Map<Item, Integer>> result = new HashMap<>();

        List<Order> orders = getAllTrackedOrdersById(userId);
        for (Order order : orders) {
            Map<Item, Integer> map = itemService.getOrderNotNullItems(order.getOrderId());
            result.put(order, map);
        }
      return result;
    }

    @Override
    public void repeatOrder(Order repeatedOrder, int orderId) {
/*

        Map<Item, Integer> items = itemService.getOrderNotNullItems(orderId);


        update(repeatedOrder);

        orderId = repeatedOrder.getOrderId();



        for (Item item : repeatedOrder.getItems()) {
            for (Map.Entry<Item, Integer> itemEntry : items.entrySet()) {
                if(item.getItemId()== itemEntry.getKey().getItemId()){
                    for (int i = 0; i < itemEntry.getValue(); i++) {
                        int availibleCount = item.getAvailableCount();
                        if(availibleCount==0)
                            break;
                        else{
                            availibleCount--;
                            item.setAvailableCount(availibleCount);
                            itemService.update(item);
                            updateQuantity(userService.getAuthirizedUserId(), orderId, itemDao.orderedItemQuantity(orderId,item.getItemId())+1);
                        }
                    }
                }
            }

        }
*/


    }
}
