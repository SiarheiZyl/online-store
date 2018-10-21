package com.online_market.dao;


import com.online_market.entity.Item;
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
}
