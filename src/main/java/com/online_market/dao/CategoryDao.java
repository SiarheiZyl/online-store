package com.online_market.dao;

import com.online_market.entity.Category;

import java.util.List;

public interface CategoryDao {

    List<Category> listCategories();

    void saveOrUpdate(Category category);

    Category findByName(String categoryName);
}
