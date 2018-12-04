package com.online_market.dao;

import com.online_market.entity.Category;
import com.online_market.entity.Item;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Class implementing ${@link CategoryDao}
 *
 * @author Siarhei
 * @version 1.0
 */
@Repository
public class CategoryDaoImpl extends GenericDaoImpl<Category> implements CategoryDao {

    final static Logger logger = Logger.getLogger(CategoryDao.class);

    private final SessionFactory sessionFactory;

    /**
     * Injecting constructor for SessionFactory exemplar
     */
    @Autowired
    public CategoryDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Getting category by name
     *
     * @param categoryName category's name
     * @return an exemplar ${@link Category}
     */
    @Override
    public Category findByName(String categoryName) {

        String s = "select c from Category c where categoryName = :categoty_name";
        Query query = sessionFactory.getCurrentSession().createQuery(s);
        query.setParameter("categoty_name", categoryName);
        List list = query.list();

        return list.size() > 0 ? (Category) list.get(0) : null;
    }

    /**
     * Finding all category depends on isShown
     *
     * @return list of ${@link Category}
     */
    @Override
    public List<Category> getAllItemsWithIsShown(boolean isShown) {

        Query selectQuery = sessionFactory.getCurrentSession().createQuery("From Category as categ where categ.isShown = :isShown").setParameter("isShown", isShown);
        List<Category> list = selectQuery.list();

        return list;
    }
}
