package com.online_market.service;

import com.online_market.dao.CategoryDao;
import com.online_market.entity.Category;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Class implementing ${@link CategoryService}
 *
 * @author Siarhei
 * @version 1.0
 */
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    final static Logger logger = Logger.getLogger(CategoryService.class);

    @Autowired
    CategoryDao categoryDao;

    /**
     * Getting all categories
     *
     * @return list of ${@link Category}
     */
    @Override
    public List<Category> listCategories() {

        logger.info("Getting list of categories(called listCategories())");

        return categoryDao.getAll("Category");
    }

    /**
     * Saving category
     *
     * @param categName category name
     * @return lower camel category name
     */
    @Override
    public String save(String categName) {

        logger.info("Saving category(called save(String categName))");

        Category category = new Category();

        categName = categName.trim();
        String categoryName = categName.toUpperCase().toCharArray()[0] + categName.toLowerCase().substring(1, categName.length());
        category.setCategoryName(categoryName);

        categoryDao.saveOrUpdate(category);

        return categoryName;
    }
}
