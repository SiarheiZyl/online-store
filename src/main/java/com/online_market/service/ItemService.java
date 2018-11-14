package com.online_market.service;

import com.online_market.entity.Item;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * Service interface for ${@link Item}
 *
 * @author Siarhei
 * @version 1.0
 */
public interface ItemService {

    List<Item> itemList();

    Item getById(int id);

    void update(int itemId, String itemName, String category, String author, String country, int height, int width, int availableCount, int price);

    void updateQuantity(Item item);

    void addNewItem(String itemName, int avalCount, int price, String itemCateg, String author, String country, int height, int width);

    Map<Item, Integer> getOrderNotNullItems(int orderId);

    List<Item> getFilteredItemsByAuthor(List<Item> items, String author);

    List<Item> getFilteredItemsByCountry(List<Item> items, String country);

    List<Item> getFilteredItemsByMaxWidth(List<Item> items, int maxWidth);

    List<Item> getFilteredItemsByMaxHeight(List<Item> items, int maxHeight);

    List<Item> getFilteredItemsByAllParams(String author, String country, int maxWidth, int maxHeight);

    List<Item> getFilteredItemsByCategory(List<Item> items, String category);
}
