package com.online_market.dao;


import com.online_market.entity.Item;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ItemDaoImpl implements ItemDao {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public List<Item> itemList() {

        String s = "select e from Item e";
        Query query = sessionFactory.getCurrentSession().createQuery(s);

        return query.getResultList();

    }

    @Override
    public Item getById(int id) {
        Session session = sessionFactory.openSession();
        Item item = session.get(Item.class, id);
        Hibernate.initialize(item);
        session.close();

        return item;
    }

    @Override
    public void update(Item item) {

        String s = "update items SET available_count = :available_count where item_id = :item_id";
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery(s);
        query.setParameter("available_count", item.getAvailableCount());
        query.setParameter("item_id", item.getItemId());
        query.executeUpdate();
        sessionFactory.getCurrentSession().clear();
        sessionFactory.getCurrentSession().update(item);
        sessionFactory.getCurrentSession().flush();

    }

    @Override
    public int orderedItemQuantity(int orderId, int itemId) {
        String s = "select quantity from ordered_items where orders = :orders AND item = :item";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(s);
        query.setParameter("orders", orderId);
        query.setParameter("item", itemId);
        List list = query.list();

        return list == null ? 0 : (Integer)list.get(0);
    }
    @Override
    public int orderedItemId(int orderId, int itemId) {
        String s = "select ordered_item_id from ordered_items where orders = :orders AND item = :item";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(s);
        query.setParameter("orders", orderId);
        query.setParameter("item", itemId);
        List list = query.list();

        return (Integer)list.get(0);
    }

    @Override
    public void updateQuantityOfOrderedItem(int orderId, int itemId, int quantity) {
        int q = orderedItemQuantity(orderId, itemId) + 1 ;
        String s = "update ordered_items SET quantity = :quantity where orders = :orders AND item = :item";
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery(s);
        query.setParameter("quantity", q);
        query.setParameter("orders", orderId);
        query.setParameter("item", itemId);
        query.executeUpdate();
    }


}
