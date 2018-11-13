package com.online_market.dao;


import com.online_market.entity.Item;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Dao interface for ${@link Item}
 *
 * @author Siarhei
 * @version 1.0
 */
public interface ItemDao extends GenericDao<Item> {

    void updateQuantity(Item item);

    int orderedItemQuantity(int orderId, int itemId);

    void updateQuantityOfOrderedItem(int orderId, int itemId, int quantity);

    Map<Item, Integer> getNotNullItemsInBucket(int orderId);

}
