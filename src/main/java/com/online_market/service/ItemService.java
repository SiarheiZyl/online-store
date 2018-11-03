package com.online_market.service;

import com.online_market.entity.Item;

import java.util.List;
import java.util.Map;

public interface ItemService {

    List<Item> itemList();

    Item getById(int id);

    Map<Item, Integer> getBucketItems(int orderId);

    List<Item> getFilteredItemsByAuthor(List<Item> items, String author);

    List<Item> getFilteredItemsByCountry(List<Item> items, String country);

    List<Item> getFilteredItemsByMaxWidth(List<Item> items, int maxWidth);

    List<Item> getFilteredItemsByMaxHeight(List<Item> items, int maxHeight);

    List<Item> getFilteredItemsByAllParams(String author, String country, int maxWidth, int maxHeight);

    List<Item> getFilteredItemsByCategory(List<Item> items, String category);
}
