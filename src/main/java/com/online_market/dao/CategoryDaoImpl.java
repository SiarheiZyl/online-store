package com.online_market.dao;

import com.online_market.entity.Category;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryDaoImpl implements CategoryDao {

    final static Logger logger = Logger.getLogger(CategoryDao.class);

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public List<Category> listCategories() {
        String s = "select e from Category e";
        Query query = sessionFactory.getCurrentSession().createQuery(s);

        return query.getResultList();
    }

    @Override
    public void saveOrUpdate(Category category) {
        sessionFactory.getCurrentSession().saveOrUpdate(category);
    }

    @Override
    public Category findByName(String categoryName) {

        String s = "select * from categories where category_name = :categoty_name";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(s).addEntity(Category.class);
        query.setParameter("categoty_name", categoryName);
        List list = query.list();

        return list.size()>0 ? (Category) list.get(0) : null;
    }
}
