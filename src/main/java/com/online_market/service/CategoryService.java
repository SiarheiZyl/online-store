package com.online_market.service;

import com.online_market.entity.Category;

import java.util.List;

/**
 * Service interface for ${@link Category}
 *
 * @author Siarhei
 * @version 1.0
 */
public interface CategoryService {

    List<Category> listCategories();

    String save(String categName);

    List<Category> getAllItemsWithIsShown(boolean isShown);

    void changeVisibilityOfCategory(String category);

    Category findByName(String name);
}
