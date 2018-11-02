package com.online_market.service;

import com.online_market.entity.Item;

import java.util.List;
import java.util.Map;

public interface ItemService {

    List<Item> itemList();

    Item getById(int id);

    Map<Item, Integer> getBucketItems(int orderId);
}
