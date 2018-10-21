package com.online_market.dao;


import com.online_market.entity.Item;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface ItemDao {

    List<Item> itemList();
}
