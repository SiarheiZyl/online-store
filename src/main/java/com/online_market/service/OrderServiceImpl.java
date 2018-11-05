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

import javax.servlet.http.HttpSession;
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

    @Override
    public void addToBucket(Item it, int userId) {
        Item item = itemDao.getById(it.getItemId());

        if (item.getAvailableCount()>0) {

            Order userBucket = getBucketOrder(userId);
            List<Item> itemList = userBucket.getItems()!=null ? userBucket.getItems() : new ArrayList<>();

            int quantity = 0;

            for (Item item1 :itemList) {
                if(item1.getItemId() == item.getItemId()){
                    item1.setAvailableCount(item1.getAvailableCount()-1);
                }
            }
            //}
            if(item.getAvailableCount()!=0) {
                item.setAvailableCount(item.getAvailableCount() - 1);
                itemDao.update(item);
            }

            userBucket.setUser(userService.getById(userId));
            userBucket.setItems(itemList);
            update(userBucket);

            quantity = itemDao.orderedItemQuantity(userBucket.getOrderId(), item.getItemId()) + 1;

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

    @Override
    public void addItemToSession(int itemId, HttpSession session) {

        Map<Item, Integer> itemMap = (Map<Item, Integer>) session.getAttribute("basket");
        Item item1 = itemService.getById(itemId);
        int quantity = 1;
        if(item1.getAvailableCount()!=0) {

        if(itemMap.containsKey(item1)) {
            quantity = itemMap.get(item1) + 1;
            itemMap.remove(item1);
        }

            item1.setAvailableCount(item1.getAvailableCount()-1);
            itemService.update(item1);

            itemMap.put(item1, quantity);
            session.setAttribute("basket", itemMap);
        }
    }

/*    @Override
    public void removeItemFromSession(int itemId, int quantity, HttpSession session) {
        Item item = itemDao.getById(itemId);
        item.setAvailableCount(item.getAvailableCount()+quantity);
        itemDao.update(item);
        updateQuantity(userId, itemId, 0);
    }*/

    @Override
    public void addFromSessionToBucket(Map<Item, Integer> itemMap, int userId) {
        Order userBucket = getBucketOrder(userId);
        for (Map.Entry<Item, Integer> itemEntry : itemMap.entrySet()) {
          int  quantity = itemDao.orderedItemQuantity(userBucket.getOrderId(), itemEntry.getKey().getItemId()) + itemEntry.getValue();
          updateQuantity(userId,  itemEntry.getKey().getItemId(), quantity);
        }
    }
}
