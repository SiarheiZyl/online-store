package com.online_market.service;

import com.online_market.entity.Item;

import java.util.List;

public interface ItemService {

    List<Item> itemList();

    Item getById(int id);
}
