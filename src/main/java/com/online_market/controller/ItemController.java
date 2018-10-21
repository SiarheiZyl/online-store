package com.online_market.controller;


import com.online_market.entity.Item;
import com.online_market.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ItemController {

    @Autowired
    ItemService itemService;

    @GetMapping("items")
    public String itemList(Model model){

        model.addAttribute("item", new Item());
        model.addAttribute("itemList", itemService.itemList());

        System.out.println(itemService.itemList().get(0));
        return "itemList";
    }

}
