package com.online_market.service;

import com.online_market.dao.CategoryDao;
import com.online_market.dao.ItemDao;
import com.online_market.entity.Category;
import com.online_market.entity.Item;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
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

    private final CategoryDao categoryDao;

    private final ItemDao itemDao;

    /**
     * Injecting constructor
     * @param categoryDao category DAO
     */
    @Autowired
    public CategoryServiceImpl(CategoryDao categoryDao, ItemDao itemDao) {
        this.categoryDao = categoryDao;
        this.itemDao = itemDao;
    }

    /**
     * Getting all categories
     *
     * @return list of ${@link Category}
     */
    @Override
    public List<Category> listCategories() {

        logger.info("Getting list of categories(called listCategories())");

        List<Category> list = categoryDao.getAll("Category");

        list.sort(Comparator.comparing(Category::getCategoryName));

        return list;
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

    /**
     * Finding all category depends on isShown
     *
     * @return list of ${@link Category}
     */
    public List<Category> getAllItemsWithIsShown(boolean isShown){

        List<Category> list  = categoryDao.getAllItemsWithIsShown(isShown);

        list.sort(Comparator.comparing(Category::getCategoryName));

        return list;
    }

    @Override
    public void changeVisibilityOfCategory(String category) {

        Category category1  = categoryDao.findByName(category);


        category1.setShown(!category1.isShown());

        categoryDao.update(category1);

        boolean isShown = category1.isShown();
        for (Item item : itemDao.getAll("Item")) {
            if (item.getCategory().getCategoryName().toLowerCase().equals(category.toLowerCase())){
                item.setShown(isShown);
                itemDao.update(item);
            }
        }
    }

    @Override
    public Category findByName(String name) {
        return categoryDao.findByName(name);
    }
}
