package com.online_market.service;

import com.online_market.dao.CategoryDao;
import com.online_market.entity.Category;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    final static Logger logger = Logger.getLogger(CategoryService.class);

    @Autowired
    CategoryDao categoryDao;

    @Override
    public List<Category> listCategories() {
        return categoryDao.listCategories();
    }
}
