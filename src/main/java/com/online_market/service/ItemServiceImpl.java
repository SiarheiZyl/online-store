package com.online_market.service;


import com.online_market.dao.ItemDao;
import com.online_market.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {

    @Autowired
    ItemDao itemDao;

    @Override
    public List<Item> itemList() {
        return itemDao.itemList();
    }

    @Override
    public Item getById(int id) {
        return itemDao.getById(id);
    }

    @Override
    public Map<Item, Integer> getBucketItems(int orderId) {
        return itemDao.getNotNullItemsInBucket(orderId);
    }
}
