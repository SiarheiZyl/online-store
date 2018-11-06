package com.online_market.service;

import com.online_market.dao.ItemDao;
import com.online_market.dao.OrderDao;
import com.online_market.entity.Item;
import com.online_market.entity.Order;
import com.online_market.entity.User;
import com.online_market.entity.enums.OrderStatus;
import com.online_market.entity.enums.PaymentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


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
            if(order.getUser().getId() == userId && order.getDeliveryMethod()!=null && order.getPaymentMethod()!=null)
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

    @Override
    public Map<User, Double> getTopUsers() {
        Map<User, Double> map = new HashMap<>();

        List<User> users = userService.findAll();
        for (User user :users) {
            double sum = 0.0;
            for (Order order : getAllTrackedOrdersById(user.getId())) {
                sum += order.getAmount();
            }
            if(sum > 0.0)
            map.put(user, sum);
        }

        Map<User, Double> result = new LinkedHashMap<>();
        map.entrySet().stream()
                .sorted(Map.Entry.<User, Double>comparingByValue().reversed()).limit(10)
                .forEachOrdered(x -> result.put(x.getKey(), x.getValue()));

        return  result;
    }

    @Override
    public Map<Item, Integer> getTopItems() {
        Map<Item, Integer> map = new HashMap<>();

        List<Order> orders = getAllTrackedOrders();

        for (Order order : orders) {
            Map<Item, Integer> itemMap = itemService.getOrderNotNullItems(order.getOrderId());
            for (Map.Entry<Item, Integer> itemEntry : itemMap.entrySet()) {
                if(map.containsKey(itemEntry.getKey())){
                    int quantity = map.get(itemEntry.getKey());
                    map.put(itemEntry.getKey(), quantity + itemEntry.getValue());
                }

                else{
                    map.put(itemEntry.getKey(), itemEntry.getValue());
                }
            }

        }

        Map<Item, Integer> result = new LinkedHashMap<>();
        map.entrySet().stream()
                .sorted(Map.Entry.<Item, Integer>comparingByValue().reversed()).limit(10)
                .forEachOrdered(x -> result.put(x.getKey(), x.getValue()));

        return  result;
    }

    @Override
    public Map<String, Double> getIncome() {

        Map<String, Double> incomeMap = new HashMap<>();
        incomeMap.put("month", 0.0);
        incomeMap.put("week", 0.0);
        incomeMap.put("day", 0.0);

        for (Order order : getAllTrackedOrders()) {
            LocalDate now = LocalDate.now();
            LocalDate previousDay = now.minusDays(1);
            LocalDate previousWeek = now.minusWeeks(1);
            LocalDate previousMonth = now.minusMonths(1);

            if (order.getPaymentStatus()==PaymentStatus.PAID) {
                if(order.getDate().toLocalDate().isAfter(previousDay)){
                    double incomeDay = incomeMap.get("day") + order.getAmount();
                    double incomeWeek = incomeMap.get("week") + order.getAmount();
                    double incomeMonth = incomeMap.get("month") + order.getAmount();

                    incomeMap.put("day", incomeDay);
                    incomeMap.put("week", incomeWeek);
                    incomeMap.put("month", incomeMonth);
                }

                else if(order.getDate().toLocalDate().isAfter(previousWeek)){
                    double incomeWeek = incomeMap.get("week") + order.getAmount();
                    double incomeMonth = incomeMap.get("month") + order.getAmount();

                    incomeMap.put("week", incomeWeek);
                    incomeMap.put("month", incomeMonth);
                }

                else if(order.getDate().toLocalDate().isAfter(previousMonth)){
                    double incomeMonth = incomeMap.get("month") + order.getAmount();

                    incomeMap.put("month", incomeMonth);
                }
            }
        }

    return incomeMap;
    }
}
