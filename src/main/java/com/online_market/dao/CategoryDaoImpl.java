package com.online_market.dao;

import com.online_market.entity.Category;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryDaoImpl implements CategoryDao {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public List<Category> listCategories() {
        String s = "select e from Category e";
        Query query = sessionFactory.getCurrentSession().createQuery(s);

        return query.getResultList();
    }
}
