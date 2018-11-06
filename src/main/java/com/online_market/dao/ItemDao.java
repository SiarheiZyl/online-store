package com.online_market.dao;


import com.online_market.entity.Item;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


public interface ItemDao {

    List<Item> itemList();

    Item getById(int id);

    void update(Item item);

    int orderedItemQuantity(int orderId, int itemId);

    int orderedItemId(int orderId, int itemId);

    void updateQuantityOfOrderedItem(int orderId, int itemId, int quantity);

    Map<Item, Integer> getNotNullItemsInBucket(int orderId);

}
