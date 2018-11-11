package com.online_market.service;

import com.online_market.dao.ItemDao;
import com.online_market.dao.OrderDao;
import com.online_market.dao.UserDao;
import com.online_market.entity.Item;
import com.online_market.entity.Order;
import com.online_market.entity.User;
import com.online_market.entity.enums.PaymentStatus;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

/**
 * Class implementing ${@link OrderService}
 * @author Siarhei
 * @version 1.0
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    final static Logger logger = Logger.getLogger(OrderService.class);

    @Autowired
    OrderDao orderDao;

    @Autowired
    ItemDao itemDao;

    @Autowired
    UserDao userDao;

    @Autowired
    UserService userService;

    @Autowired
    ItemService itemService;

    /**
     * Saving order
     * @param order order
     */
    @Override
    public void save(Order order) {

        logger.info("Saving order(called save(Order order))");

        orderDao.save(order);
    }

    /**
     * Getting order by id
     * @param id id
     * @return order
     */
    @Override
    public Order getById(int id) {

      logger.info("Getting order by id(called getById(int id))");

      return orderDao.getById(id);
    }

    /**
     * Updating order
     * @param order order
     */
    @Override
    public void update(Order order) {

        logger.info("Updating order");

        orderDao.update(order);

        logger.info("Order was updated");
    }

    /**
     * Getting user's order list
     * @param userId user id
     * @return list of ${@link Order}
     */
    @Override
    public List<Order> userOrderList(int userId) {

        logger.info("Getting userOrderList(called userOrderList(int userId))");

        return orderDao.userOrderList(userId);
    }

    /**
     * Getting user bucket order
     * @param userId user id
     * @return bucket ${@link Order}
     */
    @Override
    public Order getBucketOrder(int userId) {

        logger.info("Getting bucket(called getBucketOrder(int userId))");

        List<Order> orders = userOrderList(userId);

        for (Order order : orders) {
            if(order.getDeliveryMethod()==null && order.getPaymentMethod()==null)
                return order;
        }

        Order userBucket = new Order();
        List<Item> items = itemDao.itemList();
        userBucket.setItems(items);
        userBucket.setUser(userDao.getById(userId));
        update(userBucket);

        return userBucket;
    }

    /**
     * Saving items from bucket to orders
     * @param order order
     * @param userId user id
     */
   @Override
    public void saveBucketToOrders(Order order, int userId) {

        logger.info("Saving bucket to orders(called saveBucketToOrders(Order order, int userId))");

        Order order1 = getBucketOrder(userId);

        order1.setDeliveryMethod(order.getDeliveryMethod());
        order1.setPaymentMethod(order.getPaymentMethod());
        java.util.Date date1 = new java.util.Date();
        order1.setDate(new Date(date1.getTime()));
        order1.setAmount(order.getAmount());

        update(order1);
    }

    /**
     * Adding item to bucket
     * @param itemId item id
     * @param userId user id
     */
    @Override
    public void addToBucket(int itemId, int userId) {

        logger.info("Saving item to bucket(called addToBucket(int itemId, int userId))");

        Item item = itemDao.getById(itemId);

        if (item.getAvailableCount()>0) {

            Order userBucket = getBucketOrder(userId);
            List<Item> itemList = userBucket.getItems()!=null ? userBucket.getItems() : new ArrayList<>();

            int quantity;

            for (Item item1 :itemList) {
                if(item1.getItemId() == item.getItemId()){
                    item1.setAvailableCount(item1.getAvailableCount()-1);
                }
            }
            if(item.getAvailableCount()!=0) {
                item.setAvailableCount(item.getAvailableCount() - 1);
                itemDao.updateQuantity(item);
            }

            userBucket.setUser(userDao.getById(userId));
            userBucket.setItems(itemList);
            update(userBucket);

            quantity = itemDao.orderedItemQuantity(userBucket.getOrderId(), item.getItemId()) + 1;

            updateQuantity(userId, item.getItemId(), quantity);

         logger.info("Item was saved");
        }

        else {
            logger.warn("Item's quantity = 0");
        }
    }

    /**
     * Updating quantity of ordered item
     * @param userId user id
     * @param itemId item id
     * @param quantity quantity
     */
    @Override
    public void updateQuantity(int userId, int itemId, int quantity) {

        logger.info("Updating item quantity(called updateQuantity(int userId, int itemId, int quantity))");

        itemDao.updateQuantityOfOrderedItem(getBucketOrder(userId).getOrderId(), itemId, quantity);

        logger.info("Item's quantity was updated");
    }

    /**
     * Setting item's quantity
     * @param orderId order id
     * @param itemId item id
     * @param quantity quantity
     */
    @Override
    public void setQuantity(int orderId, int itemId, int quantity) {

        logger.info("Setting item quantity(called setQuantity(int userId, int itemId, int quantity))");

        itemDao.updateQuantityOfOrderedItem(orderId, itemId, quantity);

        logger.info("Item's quantity was updated");

    }


    /**
     * Removing item from bucket
     * @param itemId item id
     * @param userId user id
     * @param quantity quantity
     */
    @Override
    public void removeFromBucket(int itemId, int userId, int quantity) {

        logger.info("Removing item from bucket(called removeFromBucket(int itemId, int userId, int quantity))");

        Item item = itemDao.getById(itemId);
        item.setAvailableCount(item.getAvailableCount()+quantity);
        itemDao.updateQuantity(item);

        logger.info("Item was removed");
    }

    /**
     * Getting all tracked orders
     * @return list of ${@link Order}
     */
    @Override
    public List<Order> getAllTrackedOrders() {

        logger.info("Getting all tracked orders(called getAllTrackedOrders())");

        List<Order> orders = orderDao.getAllOrders();

        List<Order> list = new ArrayList<>();

        for (Order order : orders) {
            if(order.getDeliveryMethod()!=null && order.getPaymentMethod()!=null)
                list.add(order);
        }

        return list;
    }

    /**
     * Getting all user's tracked orders
     * @param userId user id
     * @return list of ${@link Order}
     */
    @Override
    public List<Order> getAllTrackedOrdersById(int userId) {

        logger.info("Getting all tracked orders by id(called getAllTrackedOrdersById(int userId))");

        List<Order> result = new ArrayList<>();

        List<Order> orders = getAllTrackedOrders();

        for (Order order : orders) {
            if(order.getUser().getId() == userId && order.getDeliveryMethod()!=null && order.getPaymentMethod()!=null)
                result.add(order);
        }
        return result;
    }

    /**
     * Getting user's history of orders
     * @param userId user id
     * @return map where key is ${@link Order}
     * and value is map where id is ${@link Item} and value is quantity
     */
    @Override
    public Map<Order, Map<Item, Integer>> getHistoryOfOrders(int userId) {

        logger.info("Getting history of orders(called getHistoryOfOrders(int userId))");

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
    }

    /**
     * Adding item to session(for unauthorized users)
     * @param itemId item id
     * @param session HttpSession
     */
    @Override
    public void addItemToSession(int itemId, HttpSession session) {

        logger.info("Adding item to session(called addItemToSession(int itemId, HttpSession session))");

        Map<Item, Integer> itemMap = (Map<Item, Integer>) session.getAttribute("basket");
        Item item1 = itemService.getById(itemId);
        int quantity = 1;
        if(item1.getAvailableCount()!=0) {

        if(itemMap.containsKey(item1)) {
            quantity = itemMap.get(item1) + 1;
            itemMap.remove(item1);
        }

            item1.setAvailableCount(item1.getAvailableCount()-1);
            itemDao.updateQuantity(item1);

            itemMap.put(item1, quantity);
            session.setAttribute("basket", itemMap);
        }

        logger.info("Item was added to session");
    }


    /**
     * Adding item from session to bucket(for unauthorized users)
     * @param itemMap map where key is ${@link Item} and value is quantity
     * @param userId user id
     */
    @Override
    public void addFromSessionToBucket(Map<Item, Integer> itemMap, int userId) {

        logger.info("Adding items from session to bucket(called addFromSessionToBucket(Map<Item, Integer> itemMap, int userId))");

        Order userBucket = getBucketOrder(userId);
        for (Map.Entry<Item, Integer> itemEntry : itemMap.entrySet()) {
          int  quantity = itemDao.orderedItemQuantity(userBucket.getOrderId(), itemEntry.getKey().getItemId()) + itemEntry.getValue();
          updateQuantity(userId,  itemEntry.getKey().getItemId(), quantity);
        }

        logger.info("All items were added to bucket");
    }

    /**
     * Getting top of users
     * @return map where key is ${@link User} and value is spent money
     */
    @Override
    public Map<User, Double> getTopUsers() {

        logger.info("Getting topUsers(called getTopUsers())");

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

    /**
     * Getting top of items
     * @return map where key is ${@link Item} and value is item's quantity
     */
    @Override
    public Map<Item, Integer> getTopItems() {

        logger.info("Getting topItems(called getTopItems())");

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

    /**
     * Getting income
     * @return map where key is period and value is income
     */
    @Override
    public Map<String, Double> getIncome() {

        logger.info("Getting income(called getIncome())");

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
