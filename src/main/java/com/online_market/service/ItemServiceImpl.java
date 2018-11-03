package com.online_market.service;


import com.online_market.dao.ItemDao;
import com.online_market.entity.Item;
import com.online_market.entity.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    @Override
    public List<Item> getFilteredItemsByAuthor(List<Item> items, String author) {
        List<Item> result = new ArrayList<>();

        for (Item item : items) {
            if(item.getParams().getAuthor().equals(author))
                result.add(item);
        }
        return result;
    }

    @Override
    public List<Item> getFilteredItemsByCountry(List<Item> items, String country) {
        List<Item> result = new ArrayList<>();

        for (Item item : items) {
            if(item.getParams().getCountry().equals(country))
                result.add(item);
        }
        return result;
    }

    @Override
    public List<Item> getFilteredItemsByMaxWidth(List<Item> items, int maxWidth) {
        List<Item> result = new ArrayList<>();

        for (Item item : items) {
            if(item.getParams().getWidth()<=maxWidth)
                result.add(item);
        }
        return result;
    }

    @Override
    public List<Item> getFilteredItemsByMaxHeight(List<Item> items, int maxHeight) {
        List<Item> result = new ArrayList<>();

        for (Item item : items) {
            if(item.getParams().getHeight()<=maxHeight)
                result.add(item);
        }
        return result;
    }

    @Override
    public List<Item> getFilteredItemsByAllParams(String author, String country, int maxWidth, int maxHeight) {
        List<Item> items = itemList();
        if(author==null || !author.equals(""))
            items = getFilteredItemsByAuthor(items, author);
        if(country==null || !country.equals(""))
            items = getFilteredItemsByCountry(items, country);
        if(maxWidth>0)
            items = getFilteredItemsByMaxWidth(items, maxWidth);
        if(maxHeight>0)
            items = getFilteredItemsByMaxWidth(items, maxHeight);

        return items;
    }
}
