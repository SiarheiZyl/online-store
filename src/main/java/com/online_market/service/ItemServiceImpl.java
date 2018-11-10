package com.online_market.service;


import com.online_market.dao.CategoryDao;
import com.online_market.dao.ItemDao;
import com.online_market.dao.ParamDao;
import com.online_market.entity.Category;
import com.online_market.entity.Item;
import com.online_market.entity.Param;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class implementing ${@link ItemService}
 * @author Siarhei
 * @version 1.0
 */
@Service
@Transactional
public class ItemServiceImpl implements ItemService {

    final static Logger logger = Logger.getLogger(ItemService.class);

    @Autowired
    ItemDao itemDao;

    @Autowired
    ParamDao paramDao;

    @Autowired
    CategoryDao categoryDao;

    /**
     * Getting all items
     * @return list of ${@link Item}
     */
    @Override
    public List<Item> itemList() {

        logger.info("Getting itemList(called itemList())");

        return itemDao.itemList();
    }

    /**
     * Getting item by id
     * @param id id
     * @return item
     */
    @Override
    public Item getById(int id) {

        logger.info("Getting Item by id(called getById(int id))");

        return itemDao.getById(id);
    }

    /**
     * Updating item
     * @param item item
     */
    @Override
    public void update(Item item) {

        logger.info("Starting updating item(called update(Item item))");

        itemDao.updateQuantity(item);

        logger.info("Item was updated");
    }

    /**
     * Adding new item
     * @param itemName name
     * @param avalCount availible count
     * @param price price
     * @param itemCateg category
     * @param author author
     * @param country country
     * @param height height
     * @param width width
     */
    @Override
    public void addNewItem(String itemName, int avalCount, int price, String itemCateg, String author, String country, int height, int width) {

        logger.info("Adding a new item(called addNewItem()) ");

        Param param = new Param();

        param.setAuthor(author);
        param.setCountry(country);
        param.setHeight(height);
        param.setWidth(width);

        paramDao.save(param);

        Category category = categoryDao.findByName(itemCateg);

        Item item = new Item();

        item.setItemName(itemName);
        item.setAvailableCount(avalCount);
        item.setPrice((double)price);
        item.setParams(param);
        item.setCategory(category);

        itemDao.save(item);

        logger.info("Item was saved");
    }

    /**
     * Getting ordered items
     * @param orderId order id
     * @return map where key is ${@link Item} and value is quantity
     */
    @Override
    public Map<Item, Integer> getOrderNotNullItems(int orderId) {

        logger.info("Getting ordered items by id(called getOrderNotNullItems(int orderId))");

        return itemDao.getNotNullItemsInBucket(orderId);
    }

    /**
     * Getting filtered items by author
     * @param items items
     * @param author author
     * @return list of ${@link Item}
     */
    @Override
    public List<Item> getFilteredItemsByAuthor(List<Item> items, String author) {

        logger.info("Getting filtering items by author(called getFilteredItemsByAuthor(List<Item> items, String author))");

        List<Item> result = new ArrayList<>();

        for (Item item : items) {
            if(item.getParams().getAuthor().equals(author))
                result.add(item);
        }
        return result;
    }

    /**
     * Getting filtered items by country
     * @param items items
     * @param country country
     * @return list of ${@link Item}
     */
    @Override
    public List<Item> getFilteredItemsByCountry(List<Item> items, String country) {

        logger.info("Getting filtering items by country(called getFilteredItemsCountry(List<Item> items, String country))");


        List<Item> result = new ArrayList<>();

        for (Item item : items) {
            if(item.getParams().getCountry().equals(country))
                result.add(item);
        }
        return result;
    }

    /**
     * Getting filtered items by maxWidth
     * @param items items
     * @param maxWidth maxWidth
     * @return list of ${@link Item}
     */
    @Override
    public List<Item> getFilteredItemsByMaxWidth(List<Item> items, int maxWidth) {

        logger.info("Getting filtering items by maxWidth(called getFilteredItemsMaxWidth(List<Item> items, String maxWidth))");

        List<Item> result = new ArrayList<>();

        for (Item item : items) {
            if(item.getParams().getWidth()<=maxWidth)
                result.add(item);
        }
        return result;
    }

    /**
     * Getting filtered items by maxHeight
     * @param items items
     * @param maxHeight maxHeight
     * @return list of {@link Item}
     */
    @Override
    public List<Item> getFilteredItemsByMaxHeight(List<Item> items, int maxHeight) {

        logger.info("Getting filtering items by maxHeight(called getFilteredItemsMaxHeight(List<Item> items, String maxHeight))");

        List<Item> result = new ArrayList<>();

        for (Item item : items) {
            if(item.getParams().getHeight()<=maxHeight)
                result.add(item);
        }
        return result;
    }

    /**
     * Getting filtered items by ${@link Param}
     * @param author author
     * @param country country
     * @param maxWidth maxWidth
     * @param maxHeight maxHeight
     * @return list of ${@link Item}
     */
    @Override
    public List<Item> getFilteredItemsByAllParams(String author, String country, int maxWidth, int maxHeight) {

        logger.info("Getting filtering items by AllParams(called getFilteredItemsByAllParams())");

        List<Item> items = itemList();
        if(author==null || !author.equals(""))
            items = getFilteredItemsByAuthor(items, author);
        if(country==null || !country.equals(""))
            items = getFilteredItemsByCountry(items, country);
        if(maxWidth>0)
            items = getFilteredItemsByMaxWidth(items, maxWidth);
        if(maxHeight>0)
            items = getFilteredItemsByMaxHeight(items, maxHeight);

        return items;
    }

    /**
     * Getting filtered items by category
     * @param items items
     * @param category category
     * @return list of ${@link Item}
     */
    @Override
    public List<Item> getFilteredItemsByCategory(List<Item> items, String category) {

        logger.info("Getting filtering items by category(called getFilteredItemsByCategory(List<Item> items, String category))");

        if(category.equals("ALL"))
            return items;
        List<Item> result = new ArrayList<>();

        for (Item item : items) {
            if(item.getCategory().getCategoryName().toLowerCase().equals(category.toLowerCase()))
                result.add(item);
        }

        return result;
    }
}
