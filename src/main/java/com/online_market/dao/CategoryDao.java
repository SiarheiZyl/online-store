package com.online_market.dao;

import com.online_market.entity.Category;

import java.util.List;

/**
 * Dao interface for ${@link Category}
 * @author Siarhei
 * @version 1.0
 */
public interface CategoryDao {

    List<Category> listCategories();

    void saveOrUpdate(Category category);

    Category findByName(String categoryName);
}
