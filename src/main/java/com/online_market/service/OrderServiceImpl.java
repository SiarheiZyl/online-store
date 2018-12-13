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
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.TextMessage;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

/**
 * Class implementing ${@link OrderService}
 *
 * @author Siarhei
 * @version 1.0
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    /**
     * Apache log4j object is used to log all important info
     */
    final static Logger logger = Logger.getLogger(OrderService.class);

    /**
     * Order dao bean
     */
    private final OrderDao orderDao;

    /**
     * Item dao bean
     */
    private final ItemDao itemDao;

    /**
     * User dao bean
     */
    private final UserDao userDao;

    /**
     * Object which allows us send messages to JMS server
     */
    private final JmsTemplate jmsTemplate;

    /**
     * Injecting constructor
     *
     * @param orderDao    order DAO
     * @param itemDao     item DAO
     * @param userDao     user DAO
     * @param jmsTemplate jmsTemplate
     */
    @Autowired
    public OrderServiceImpl(OrderDao orderDao, ItemDao itemDao, UserDao userDao, JmsTemplate jmsTemplate) {
        this.orderDao = orderDao;
        this.itemDao = itemDao;
        this.userDao = userDao;
        this.jmsTemplate = jmsTemplate;
    }

    /**
     * List of top items
     */
    private List<Item> topItems = new ArrayList<>(10);

    /**
     * Saving order
     *
     * @param order order
     */
    @Override
    public void save(Order order) {

        logger.info("Saving order(called save(Order order))");

        orderDao.save(order);
    }

    /**
     * Getting order by id
     *
     * @param id id
     * @return order
     */
    @Override
    public Order getById(int id) {

        logger.info("Getting order by id(called getById(int id))");

        return orderDao.getById(Order.class, id);
    }

    /**
     * Updating order
     *
     * @param order order
     */
    @Override
    public void update(Order order) {

        logger.info("Updating order");

        orderDao.saveOrUpdate(order);

        logger.info("Order was updated");
    }

    /**
     * Getting user's order list
     *
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
     *
     * @param userId user id
     * @return bucket ${@link Order}
     */
    @Override
    public Order getBucketOrder(int userId) {

        logger.info("Getting bucket(called getBucketOrder(int userId))");

        List<Order> orders = userOrderList(userId);

        for (Order order : orders) {
            if (order.getDeliveryMethod() == null && order.getPaymentMethod() == null)
                return order;
        }

        Order userBucket = new Order();
        List<Item> items = itemDao.getAll("Item");
        userBucket.setItems(items);
        userBucket.setUser(userDao.getById(User.class, userId));
        update(userBucket);

        return userBucket;
    }

    /**
     * Saving items from bucket to orders
     *
     * @param order  order
     * @param userId user id
     */
    @Override
    public void saveBucketToOrders(Order order, int userId) {

        logger.info("Saving bucket to orders(called saveBucketToOrders(Order order, int userId))");

        Order order1 = getBucketOrder(userId);

        Map<Item, Integer> items = itemDao.getNotNullItemsInBucket(order1.getOrderId());

        for (Map.Entry<Item, Integer> item : items.entrySet()) {
            Item item1 = item.getKey();
            int quantity = item.getValue();


            if (quantity <= item1.getAvailableCount()) {
                item1.setAvailableCount(item1.getAvailableCount() - quantity);
                itemDao.updateQuantity(item1);
            } else {
                updateQuantity(userId, item1.getItemId(), item1.getAvailableCount());
            }
        }

        order1.setDeliveryMethod(order.getDeliveryMethod());
        order1.setPaymentMethod(order.getPaymentMethod());
        java.util.Date date1 = new java.util.Date();
        order1.setDate(new Date(date1.getTime()));

        update(order1);
    }

    /**
     * Adding item to bucket
     *
     * @param itemId item id
     * @param userId user id
     */
    @Override
    public void addToBucket(int itemId, int userId) {

        logger.info("Saving item to bucket(called addToBucket(int itemId, int userId))");

        Item item = itemDao.getById(Item.class, itemId);

        if (item.getAvailableCount() > 0) {

            Order userBucket = getBucketOrder(userId);
            List<Item> itemList = userBucket.getItems() != null ? userBucket.getItems() : new ArrayList<>();

            int quantity;


            userBucket.setUser(userDao.getById(User.class, userId));
            userBucket.setItems(itemList);

            userBucket.setAmount(userBucket.getAmount() + item.getPrice());

            update(userBucket);

            quantity = itemDao.orderedItemQuantity(userBucket.getOrderId(), item.getItemId()) + 1;

            updateQuantity(userId, item.getItemId(), quantity);

            logger.info("Item was saved");
        } else {
            logger.warn("Item's quantity = 0");
        }
    }

    /**
     * Adding new items to all buckets
     *
     * @param itemId item id
     */
    @Override
    public void addNewItemToBucket(int itemId) {

        List<Order> orders = orderDao.getAll("Order");
        Item newItem = itemDao.getById(Item.class, itemId);

        for (Order order : orders) {
            if (order.getDeliveryMethod() == null && order.getPaymentMethod() == null) {
                List<Item> items = order.getItems();
                items.add(newItem);
                order.setItems(items);
                update(order);
            }
        }
    }

    /**
     * Updating quantity of ordered item
     *
     * @param userId   user id
     * @param itemId   item id
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
     *
     * @param orderId  order id
     * @param itemId   item id
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
     *
     * @param itemId   item id
     * @param userId   user id
     * @param quantity quantity
     */
    @Override
    public void removeFromBucket(int itemId, int userId, int quantity) {

        logger.info("Removing item from bucket(called removeFromBucket(int itemId, int userId, int quantity))");

        Item item = itemDao.getById(Item.class, itemId);

        if (userId != 0) {
            Order bucket = getBucketOrder(userId);
            bucket.setAmount(bucket.getAmount() - (quantity * item.getPrice()));
            update(bucket);
        }

        logger.info("Item was removed");
    }

    /**
     * Getting all tracked orders
     *
     * @return list of ${@link Order}
     */
    @Override
    public List<Order> getAllTrackedOrders() {

        logger.info("Getting all tracked orders(called getAllTrackedOrders())");

        List<Order> orders = orderDao.getAll("Order");

        List<Order> list = new ArrayList<>();

        for (Order order : orders) {
            if (order.getDeliveryMethod() != null && order.getPaymentMethod() != null)
                list.add(order);
        }

        return list;
    }

    /**
     * Getting all user's tracked orders
     *
     * @param userId user id
     * @return list of ${@link Order}
     */
    @Override
    public List<Order> getAllTrackedOrdersById(int userId) {

        logger.info("Getting all tracked orders by id(called getAllTrackedOrdersById(int userId))");

        List<Order> result = new ArrayList<>();

        List<Order> orders = getAllTrackedOrders();

        for (Order order : orders) {
            if (order.getUser().getId() == userId && order.getDeliveryMethod() != null && order.getPaymentMethod() != null)
                result.add(order);
        }
        return result;
    }

    /**
     * Quantity of tracked orders
     *
     * @return quantity
     */
    @Override
    public long sizeOfTrackedOrders() {
        return orderDao.sizeOfTrackedOrders();
    }

    /**
     * Quantity of tracked orders by period
     *
     * @param from from
     * @param to   to
     * @return quantity of orders
     */
    @Override
    public long sizeOfTrackedOrdersFilteredByDate(java.util.Date from, java.util.Date to) {
        return orderDao.sizeOfTrackedOrdersFilteredByDate(from, to);
    }

    /**
     * Quantity of stored orders by period
     *
     * @param userId   user id
     * @param fromDate from
     * @param toDate   to
     * @return quantity of orders
     */
    @Override
    public long sizeOfHistoryOfOrdersFilteredByDate(int userId, java.util.Date fromDate, java.util.Date toDate) {
        return orderDao.sizeOfHistoryOfOrdersFilteredByDate(userId, fromDate, toDate);
    }

    /**
     * Getting orders for pagination
     *
     * @param pageId pageId
     * @param total  total
     * @return list of ${@link Order}
     */
    @Override
    public List<Order> getOrdersPerPage(int pageId, int total) {
        return orderDao.getOrdersPerPage(pageId, total);
    }

    /**
     * Filtering orders by date
     *
     * @param pageId   page id
     * @param pageSize page size
     * @param fromDate from
     * @param toDate   to
     * @return list of ${@link Order}
     */
    @Override
    public List<Order> getOrdersPerPageFilteredFromToDate(int pageId, int pageSize, java.util.Date fromDate, java.util.Date toDate) {
        return orderDao.getOrdersPerPageFilteredFromToDate(pageId, pageSize, fromDate, toDate);
    }

    /**
     * Filtering stored orders by date
     *
     * @param userId   user id
     * @param pageId   page id
     * @param pageSize page size
     * @param fromDate from
     * @param toDate   to
     * @return map of ${@link Order}
     */
    @Override
    public Map<Order, Map<Item, Integer>> getHistoryOfOrdersPerPageFilteredFromToDate(int userId, int pageId, int pageSize, java.util.Date fromDate, java.util.Date toDate) {

        List<Order> orders = orderDao.getHistoryOfOrdersPerPageFilteredFromToDate(userId, pageId, pageSize, fromDate, toDate);
        Map<Order, Map<Item, Integer>> result = new HashMap<>();
        for (Order order : orders) {
            Map<Item, Integer> map = itemDao.getNotNullItemsInBucket(order.getOrderId());
            result.put(order, map);
        }

        return result;
    }

    /**
     * Getting user's history of orders
     *
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
            Map<Item, Integer> map = itemDao.getNotNullItemsInBucket(order.getOrderId());
            result.put(order, map);
        }

        return result;
    }

    /**
     * Getting history of orders per page
     *
     * @param userId   user id
     * @param pageId   page id
     * @param pageSize page size
     * @return map where key is ${@link Order}
     * and value is map where id is ${@link Item} and value is quantity
     */
    @Override
    public Map<Order, Map<Item, Integer>> getHistoryOfOrdersPerPage(int userId, int pageId, int pageSize) {

        logger.info("Getting history of orders(called getHistoryOfOrdersPerPage(int id, int pageId, int pageSize)");

        Map<Order, Map<Item, Integer>> result = new HashMap<>();

        List<Order> orders = getAllTrackedOrdersById(userId);

        for (int i = pageSize * (pageId - 1); i < pageSize * pageId; i++) {

            if (i >= orders.size())
                break;
            Map<Item, Integer> map = itemDao.getNotNullItemsInBucket(orders.get(i).getOrderId());
            result.put(orders.get(i), map);
        }

        return result;
    }

    @Override
    public void repeatOrder(Order repeatedOrder, int orderId) {
    }

    /**
     * Adding item to session(for unauthorized users)
     *
     * @param itemId  item id
     * @param session HttpSession
     */
    @Override
    public void addItemToSession(int itemId, HttpSession session) {

        logger.info("Adding item to session(called addItemToSession(int itemId, HttpSession session))");

        Map<Item, Integer> itemMap = (Map<Item, Integer>) session.getAttribute("basket");
        Item item1 = itemDao.getById(Item.class, itemId);
        int quantity = 1;
        if (item1.getAvailableCount() != 0) {

            if (itemMap.containsKey(item1)) {
                quantity = itemMap.get(item1) < item1.getAvailableCount() ? itemMap.get(item1) + 1 : itemMap.get(item1);
                itemMap.remove(item1);
            }
            itemMap.put(item1, quantity);
            session.setAttribute("basket", itemMap);
        }

        logger.info("Item was added to session");
    }

    /**
     * Adding item from session to bucket(for unauthorized users)
     *
     * @param itemMap map where key is ${@link Item} and value is quantity
     * @param userId  user id
     */
    @Override
    public void addFromSessionToBucket(Map<Item, Integer> itemMap, int userId) {

        logger.info("Adding items from session to bucket(called addFromSessionToBucket(Map<Item, Integer> itemMap, int userId))");

        Order userBucket = getBucketOrder(userId);
        int amount = 0;
        for (Map.Entry<Item, Integer> itemEntry : itemMap.entrySet()) {
            int quantity = itemDao.orderedItemQuantity(userBucket.getOrderId(), itemEntry.getKey().getItemId()) + itemEntry.getValue();
            updateQuantity(userId, itemEntry.getKey().getItemId(), quantity);
            amount += itemEntry.getValue() * itemEntry.getKey().getPrice();
        }

        userBucket.setAmount(userBucket.getAmount() + amount);
        update(userBucket);

        logger.info("All items were added to bucket");
    }

    /**
     * Getting top of users
     *
     * @return map where key is ${@link User} and value is spent money
     */
    @Override
    public Map<User, Double> getTopUsers() {

        logger.info("Getting topUsers(called getTopUsers())");

        Map<User, Double> map = new HashMap<>();

        List<User> users = userDao.getAll("User");
        for (User user : users) {
            double sum = 0.0;
            for (Order order : getAllTrackedOrdersById(user.getId())) {
                sum += order.getAmount();
            }
            if (sum > 0.0)
                map.put(user, sum);
        }

        Map<User, Double> result = new LinkedHashMap<>();
        map.entrySet().stream()
                .sorted(Map.Entry.<User, Double>comparingByValue().reversed()).limit(10)
                .forEachOrdered(x -> result.put(x.getKey(), x.getValue()));

        return result;
    }

    /**
     * Getting top of items
     *
     * @return map where key is ${@link Item} and value is item's quantity
     */
    @Override
    public Map<Item, Integer> getTopItems() {

        logger.info("Getting topItems(called getTopItems())");

        Map<Item, Integer> map = new HashMap<>();

        List<Order> orders = getAllTrackedOrders();

        for (Order order : orders) {
            Map<Item, Integer> itemMap = itemDao.getNotNullItemsInBucket(order.getOrderId());
            for (Map.Entry<Item, Integer> itemEntry : itemMap.entrySet()) {
                if (map.containsKey(itemEntry.getKey())) {
                    int quantity = map.get(itemEntry.getKey());
                    map.put(itemEntry.getKey(), quantity + itemEntry.getValue());
                } else {
                    map.put(itemEntry.getKey(), itemEntry.getValue());
                }
            }

        }

        Map<Item, Integer> result = new LinkedHashMap<>();
        map.entrySet().stream()
                .sorted(Map.Entry.<Item, Integer>comparingByValue().reversed()).limit(10)
                .forEachOrdered(x -> result.put(x.getKey(), x.getValue()));

        return result;
    }

    /**
     * Getting income
     *
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

            if (order.getPaymentStatus() == PaymentStatus.PAID) {
                if (order.getDate().toLocalDate().isAfter(previousDay)) {
                    double incomeDay = incomeMap.get("day") + order.getAmount();
                    double incomeWeek = incomeMap.get("week") + order.getAmount();
                    double incomeMonth = incomeMap.get("month") + order.getAmount();

                    incomeMap.put("day", incomeDay);
                    incomeMap.put("week", incomeWeek);
                    incomeMap.put("month", incomeMonth);
                } else if (order.getDate().toLocalDate().isAfter(previousWeek)) {
                    double incomeWeek = incomeMap.get("week") + order.getAmount();
                    double incomeMonth = incomeMap.get("month") + order.getAmount();

                    incomeMap.put("week", incomeWeek);
                    incomeMap.put("month", incomeMonth);
                } else if (order.getDate().toLocalDate().isAfter(previousMonth)) {
                    double incomeMonth = incomeMap.get("month") + order.getAmount();

                    incomeMap.put("month", incomeMonth);
                }
            }
        }

        return incomeMap;
    }

    /**
     * Sending message to Jms server
     * In order to notify second app to refresh top of items.
     */
    @Override
    public void sendUpdateMessageToJms() {

        jmsTemplate.send("advertising.stand", session -> {
            TextMessage msg = session.createTextMessage();
            msg.setText("Items are up to date");
            return msg;
        });
    }

    /**
     * Updating top of items
     */
    @Override
    public void updateTopItems() {

        if (topItems.isEmpty()) {
            topItems = new ArrayList<>(getTopItems().keySet());
            sendUpdateMessageToJms();
        } else {
            List<Item> newestTop = new ArrayList<>(getTopItems().keySet());

            for (int i = 0; i < 10; i++) {
                if (topItems.get(i).getItemId() != newestTop.get(i).getItemId()) {
                    topItems = newestTop;
                    sendUpdateMessageToJms();
                    break;
                }
            }

        }
    }

    /**
     * Update quantity of items which are availible for user bucket
     *
     * @param userId user id
     */
    @Override
    public void updateBucket(int userId) {

        Order order = getBucketOrder(userId);
        Map<Item, Integer> items = itemDao.getNotNullItemsInBucket(order.getOrderId());

        for (Map.Entry<Item, Integer> item : items.entrySet()) {
            Item item1 = item.getKey();
            int quantity = item.getValue();
            if (quantity > item1.getAvailableCount()) {
                updateQuantity(userId, item1.getItemId(), item1.getAvailableCount());
            }
        }
    }
}
