package com.online_market.service;


import com.online_market.dao.ItemDao;
import com.online_market.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {

    @Autowired
    ItemDao itemDao;

    @Override
    public List<Item> itemList() {
        return itemDao.itemList();
    }
}
