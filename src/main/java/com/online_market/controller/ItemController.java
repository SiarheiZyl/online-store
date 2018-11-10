package com.online_market.controller;


import com.online_market.entity.Item;
import com.online_market.entity.Order;
import com.online_market.entity.Param;
import com.online_market.service.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ItemController {

    final static Logger logger = Logger.getLogger(ItemController.class);

    @Autowired
    ItemService itemService;

    @Autowired
    UserService userService;

    @Autowired
    OrderService orderService;

    @Autowired
    ParamService paramService;

    @Autowired
    CategoryService categoryService;


    @GetMapping("/catalog")
    public String catalog(Model model , HttpSession session){

        model.addAttribute("item", new Item());
        model.addAttribute("itemList", itemService.itemList());
        model.addAttribute("categoryList", categoryService.listCategories());

        int id = userService.getAuthirizedUserId();

        if (id!=0 && userService.getById(id).isAuth()) {
            model.addAttribute("id", id);
            model.addAttribute("user", userService.getById(id));

            //fix: userBucket
            //Order bucket = orderService.getBucketOrder(id);
        }

        else{
            Map<Item, Integer> itemMap = new HashMap<>();
            if(session.getAttribute("basket")==null)
            session.setAttribute("basket", itemMap);
        }

        return "catalog";
    }

    @GetMapping("/items")
    public String itemList2(Model model){

        model.addAttribute("it", new Item());
        model.addAttribute("itemList", itemService.itemList());
        model.addAttribute("params", new Param());

        model.addAttribute("authors", paramService.getAllAuthors());
        model.addAttribute("countries", paramService.getAllCountries());
        model.addAttribute("category", "ALL");

        int id = userService.getAuthirizedUserId();
        if (id!=0 && userService.getById(id).isAuth()) {
            model.addAttribute("id", id);
            model.addAttribute("user", userService.getById(id));

            //fix: userBucket
            //Order bucket = orderService.getBucketOrder(id);
        }

        return "itemList";
    }

    @GetMapping("/filterItems/{category}")
    public String filteredItemList2( @ModelAttribute("params") Param params,@PathVariable("category") String category, Model model){

        model.addAttribute("item", new Item());
        List<Item> itemList;
        if(params.getWidth()==0&&params.getHeight()==0&&params.getAuthor()==null&&params.getCountry()==null)
            itemList = itemService.itemList();
        else
            itemList = itemService.getFilteredItemsByAllParams(params.getAuthor(), params.getCountry(), params.getWidth(), params.getHeight());

        model.addAttribute("itemList", itemService.getFilteredItemsByCategory(itemList, category));

        model.addAttribute("authors", paramService.getAllAuthors());
        model.addAttribute("countries", paramService.getAllCountries());

        int id = userService.getAuthirizedUserId();
        if (id!=0 && userService.getById(id).isAuth()) {

            model.addAttribute("id", id);
            model.addAttribute("user", userService.getById(id));

            //fix: userBucket
            //Order bucket = orderService.getBucketOrder(id);
        }

        return "itemList";
    }
}
