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

import java.util.*;

/**
 * Class implementing ${@link ItemService}
 *
 * @author Siarhei
 * @version 1.0
 */
@Service
@Transactional
public class ItemServiceImpl implements ItemService {

    /**
     * Apache log4j object is used to log all important info
     */
    final static Logger logger = Logger.getLogger(ItemService.class);

    /**
     * Item dao bean
     */
    private final ItemDao itemDao;

    /**
     * Param dao bean
     */
    private final ParamDao paramDao;

    /**
     * Category dao bean
     */
    private final CategoryDao categoryDao;

    /**
     * Injecting constructor
     *
     * @param itemDao     item DAO
     * @param paramDao    param DAO
     * @param categoryDao category DAO
     */
    @Autowired
    public ItemServiceImpl(ItemDao itemDao, ParamDao paramDao, CategoryDao categoryDao) {
        this.itemDao = itemDao;
        this.paramDao = paramDao;
        this.categoryDao = categoryDao;
    }

    /**
     * Getting all items
     *
     * @return list of ${@link Item}
     */
    @Override
    public List<Item> itemList() {

        logger.info("Getting getAll(called getAll())");

        return itemDao.getAll("Item");
    }

    /**
     * Finding all items with isShown == true
     *
     * @return list of ${@link Item}
     */
    @Override
    public List<Item> visibleItemList() {
        return itemDao.getAllItemsWithIsShown();
    }

    /**
     * Getting item by id
     *
     * @param id id
     * @return item
     */
    @Override
    public Item getById(int id) {

        logger.info("Getting Item by id(called getById(int id))");

        return itemDao.getById(Item.class, id);
    }

    /**
     * Updating existing item
     *
     * @param itemId         item's id
     * @param itemName       item's name
     * @param category       category
     * @param author         author
     * @param country        country
     * @param height         height
     * @param width          width
     * @param availableCount available count
     * @param price          price
     */
    @Override
    public void update(int itemId, String itemName, String category, String author, String country, int height, int width, int availableCount, double price) {

        Item item = itemDao.getById(Item.class, itemId);
        Category category1 = categoryDao.findByName(category);

        item.setCategory(category1);

        Param param = paramDao.getById(Param.class, item.getParams().getParamId());

        param.setAuthor(author);
        param.setCountry(country);
        param.setHeight(height);
        param.setWidth(width);

        paramDao.update(param);

        item.setItemName(itemName);
        item.setAvailableCount(availableCount);
        item.setPrice(price);

        itemDao.update(item);
    }

    /**
     * Updating item
     *
     * @param item item
     */
    @Override
    public void updateQuantity(Item item) {

        logger.info("Starting updating item(called saveOrUpdate(Item item))");

        itemDao.updateQuantity(item);

        logger.info("Item was updated");
    }

    /**
     * Adding new item
     *
     * @param itemName  name
     * @param avalCount availible count
     * @param price     price
     * @param itemCateg category
     * @param author    author
     * @param country   country
     * @param height    height
     * @param width     width
     */
    @Override
    public int addNewItem(String itemName, int avalCount, int price, String itemCateg, String author, String country, int height, int width) {

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
        item.setPrice((double) price);
        item.setParams(param);
        item.setCategory(category);

        itemDao.save(item);

        logger.info("Item was saved");

        return item.getItemId();
    }

    /**
     * Getting ordered items
     *
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
     *
     * @param items  items
     * @param author author
     * @return list of ${@link Item}
     */
    @Override
    public List<Item> getFilteredItemsByAuthor(List<Item> items, String author) {

        logger.info("Getting filtering items by author(called getFilteredItemsByAuthor(List<Item> items, String author))");

        List<Item> result = new ArrayList<>();

        for (Item item : items) {
            if (item.getParams().getAuthor().equals(author))
                result.add(item);
        }
        return result;
    }

    /**
     * Getting filtered items by country
     *
     * @param items   items
     * @param country country
     * @return list of ${@link Item}
     */
    @Override
    public List<Item> getFilteredItemsByCountry(List<Item> items, String country) {

        logger.info("Getting filtering items by country(called getFilteredItemsCountry(List<Item> items, String country))");


        List<Item> result = new ArrayList<>();

        for (Item item : items) {
            if (item.getParams().getCountry().equals(country))
                result.add(item);
        }
        return result;
    }

    /**
     * Getting filtered items by maxWidth
     *
     * @param items    items
     * @param maxWidth maxWidth
     * @return list of ${@link Item}
     */
    @Override
    public List<Item> getFilteredItemsByMaxWidth(List<Item> items, int maxWidth) {

        logger.info("Getting filtering items by maxWidth(called getFilteredItemsMaxWidth(List<Item> items, String maxWidth))");

        List<Item> result = new ArrayList<>();

        for (Item item : items) {
            if (item.getParams().getWidth() <= maxWidth)
                result.add(item);
        }
        return result;
    }

    /**
     * Getting filtered items by maxHeight
     *
     * @param items     items
     * @param maxHeight maxHeight
     * @return list of {@link Item}
     */
    @Override
    public List<Item> getFilteredItemsByMaxHeight(List<Item> items, int maxHeight) {

        logger.info("Getting filtering items by maxHeight(called getFilteredItemsMaxHeight(List<Item> items, String maxHeight))");

        List<Item> result = new ArrayList<>();

        for (Item item : items) {
            if (item.getParams().getHeight() <= maxHeight)
                result.add(item);
        }
        return result;
    }

    /**
     * Getting filtered items by ${@link Param}
     *
     * @param author    author
     * @param country   country
     * @param maxWidth  maxWidth
     * @param maxHeight maxHeight
     * @return list of ${@link Item}
     */
    @Override
    public List<Item> getFilteredItemsByAllParams(String author, String country, int maxWidth, int maxHeight) {

        logger.info("Getting filtering items by AllParams(called getFilteredItemsByAllParams())");

        List<Item> items = itemList();
        if (author == null || !author.equals(""))
            items = getFilteredItemsByAuthor(items, author);
        if (country == null || !country.equals(""))
            items = getFilteredItemsByCountry(items, country);
        if (maxWidth > 0)
            items = getFilteredItemsByMaxWidth(items, maxWidth);
        if (maxHeight > 0)
            items = getFilteredItemsByMaxHeight(items, maxHeight);

        return items;
    }

    /**
     * Getting filtered items by category
     *
     * @param items    items
     * @param category category
     * @return list of ${@link Item}
     */
    @Override
    public List<Item> getFilteredItemsByCategory(List<Item> items, String category) {

        logger.info("Getting filtering items by category(called getFilteredItemsByCategory(List<Item> items, String category))");

        if (category.equals("ALL"))
            return items;
        List<Item> result = new ArrayList<>();

        for (Item item : items) {
            if (item.getCategory().getCategoryName().toLowerCase().equals(category.toLowerCase()))
                result.add(item);
        }

        return result;
    }

    /**
     * Getting filtered visible items by category
     *
     * @param items    items
     * @param category category
     * @return list of ${@link Item}
     */
    @Override
    public List<Item> getFilteredShownItemsByCategory(List<Item> items, String category) {

        logger.info("Getting filtering items by category(called getFilteredItemsByCategory(List<Item> items, String category))");

        List<Item> result = new ArrayList<>();

        if (category.equals("ALL")) {
            for (Item item : items) {
                if (item.isShown())
                    result.add(item);
            }

            return items;
        }

        for (Item item : items) {
            if (item.getCategory().getCategoryName().toLowerCase().equals(category.toLowerCase()) && item.isShown())
                result.add(item);
        }

        return result;
    }

    /**
     * Getting items per page
     *
     * @param pageId   page id
     * @param pageSize page size
     * @return list of ${@link Item}
     */
    @Override
    public List<Item> itemListPerPage(int pageId, int pageSize) {
        return itemDao.itemListPerPage(pageId, pageSize);
    }

    /**
     * Getting visible items per page
     *
     * @param pageId   page id
     * @param pageSize page size
     * @return list of ${@link Item}
     */
    @Override
    public List<Item> visibleItemListPerPage(int pageId, int pageSize) {
        return itemDao.visibleItemListPerPage(pageId, pageSize);
    }

    /**
     * Getting summary quantity of items in Order
     *
     * @param items items in order with quantity
     * @return quantity
     */
    @Override
    public int getOrderSize(Map<Item, Integer> items) {

        int sum = 0;

        for (Integer integer : items.values()) {
            sum += integer;
        }

        return sum;
    }

    /**
     * This function is used to find items by key word
     *
     * @param searchString string for searching
     * @return set of {@link Item}
     */
    @Override
    public Set<Item> search(String searchString) {

        Set<Item> result = new HashSet<>();

        result.addAll(getFilteredItemsByCategory(itemList(), searchString));
        result.addAll(itemDao.findItemsByCountry(searchString));
        result.addAll(itemDao.findItemsByName(searchString));
        result.addAll(itemDao.findItemsByAuthor(searchString));

        return result;
    }

    /**
     * Changing visibility of item
     *
     * @param itemId item id
     */
    @Override
    public void changeVisibilityOfItem(int itemId) {

        Item item = getById(itemId);
        item.setShown(!item.isShown());
        itemDao.update(item);
    }
}
