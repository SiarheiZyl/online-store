package com.online_market.dao;


import com.online_market.entity.Item;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Class implementing ${@link ItemDao}
 *
 * @author Siarhei
 * @version 1.0
 */
@Repository
public class ItemDaoImpl extends GenericDaoImpl<Item> implements ItemDao {

    /**
     * SessionFactory exemplar through which we get
     * sessions and perform database operations
     */
    private final SessionFactory sessionFactory;

    /**
     * Injecting constructor for SessionFactory exemplar
     */
    @Autowired
    public ItemDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Finding all items with isShown == true
     *
     * @return list of ${@link Item}
     */
    @Override
    public List<Item> getAllItemsWithIsShown() {

        Query selectQuery = sessionFactory.getCurrentSession().createQuery("From Item as item where item.isShown = true  and item.category.isShown = true");
        List<Item> list = selectQuery.list();

        return list;
    }

    /**
     * Updating quantity of item
     *
     * @param item item
     */
    @Override
    public void updateQuantity(Item item) {

        String s = "update Item SET availableCount = :available_count where itemId = :item_id";
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(s);
        query.setParameter("available_count", item.getAvailableCount());
        query.setParameter("item_id", item.getItemId());
        query.executeUpdate();
        sessionFactory.getCurrentSession().clear();
        sessionFactory.getCurrentSession().update(item);
        sessionFactory.getCurrentSession().flush();

    }

    /**
     * Getting quantity of ordered item
     *
     * @param orderId order id
     * @param itemId  item id
     * @return quantity
     */
    @Override
    public int orderedItemQuantity(int orderId, int itemId) {

        String s = "select quantity from ordered_items where orders = :orders AND item = :item";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(s);
        query.setParameter("orders", orderId);
        query.setParameter("item", itemId);
        List list = query.list();

        return list.size() == 0 ? 0 : (Integer) list.get(0);
    }

    /**
     * Updating quantity of ordered item
     *
     * @param orderId  order id
     * @param itemId   item id
     * @param quantity quantity
     */
    @Override
    public void updateQuantityOfOrderedItem(int orderId, int itemId, int quantity) {

        String s = "update ordered_items SET quantity = :quantity where orders = :orders AND item = :item";
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery(s);
        query.setParameter("quantity", quantity);
        query.setParameter("orders", orderId);
        query.setParameter("item", itemId);
        query.executeUpdate();
    }

    /**
     * Getting items from bucket where quantity > 0
     *
     * @param orderId order id
     * @return map where key is ${@link Item} and value is item's quantity
     */
    @Override
    public Map<Item, Integer> getNotNullItemsInBucket(int orderId) {

        String s = "select item from ordered_items where orders =:orders AND quantity >0";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(s);
        query.setParameter("orders", orderId);
        List<Integer> itemsId = query.list();
        List<Item> itemList = new ArrayList<>();

        Map<Item, Integer> items = new HashMap<>();

        for (Item item : getAll("Item")) {
            for (int i : itemsId) {
                if (i == item.getItemId())
                    itemList.add(item);
            }
        }

        for (Item item : itemList) {
            items.put(item, orderedItemQuantity(orderId, item.getItemId()));
        }

        return items;
    }

    /**
     * Getting items per page
     *
     * @param pageId   page id
     * @param pageSize page size
     * @return list of ${@link Item}
     */
    public List<Item> itemListPerPage(int pageId, int pageSize) {

        Query selectQuery = sessionFactory.getCurrentSession().createQuery("From Item");

        selectQuery.setFirstResult((pageId - 1) * pageSize);
        selectQuery.setMaxResults(pageSize);

        List<Item> list = selectQuery.list();

        return list;
    }

    /**
     * Getting visible items per page
     *
     * @param pageId   page id
     * @param pageSize page size
     * @return list of ${@link Item}
     */
    public List<Item> visibleItemListPerPage(int pageId, int pageSize) {

        Query selectQuery = sessionFactory.getCurrentSession().createQuery("From Item as item where item.isShown = true and item.category.isShown = true");

        selectQuery.setFirstResult((pageId - 1) * pageSize);
        selectQuery.setMaxResults(pageSize);

        List<Item> list = selectQuery.list();

        return list;
    }

    /**
     * Finding items by author
     *
     * @param author author
     * @return list of {@link Item}
     */
    @Override
    public List<Item> findItemsByAuthor(String author) {

        Query query = sessionFactory.getCurrentSession().createQuery("SELECT p FROM Item p WHERE params.author = :author");
        query.setParameter("author", author);

        return (List<Item>) query.getResultList();
    }

    /**
     * Finding items by country
     *
     * @param country country
     * @return list of {@link Item}
     */
    @Override
    public List<Item> findItemsByCountry(String country) {

        Query query = sessionFactory.getCurrentSession().createQuery("SELECT p FROM Item p WHERE params.country = :country");
        query.setParameter("country", country);

        return (List<Item>) query.getResultList();
    }


    /**
     * Finding items by name
     *
     * @param name name
     * @return list of {@link Item}
     */
    @Override
    public List<Item> findItemsByName(String name) {

        Query query = sessionFactory.getCurrentSession().createQuery("SELECT p FROM Item p WHERE itemName= :itemName");
        query.setParameter("itemName", name);

        return (List<Item>) query.getResultList();
    }
}
