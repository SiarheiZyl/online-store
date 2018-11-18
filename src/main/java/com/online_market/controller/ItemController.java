package com.online_market.controller;


import com.online_market.entity.Item;
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

/**
 * Class for mapping all paths associated with items{@link Item}
 * @author Siarhei
 * @version 1.0
 */
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

    /**
     * Get mapping for catalog
     * @param model model
     * @param session HttpSession
     * @return catalog page
     */
    @GetMapping("/catalog/{pageId}")
    public String catalog(@PathVariable("pageId") int pageId, Model model , HttpSession session){


        int pageSize = 12;

        model.addAttribute("pageId", pageId);
        model.addAttribute("pageSize", itemService.itemList().size()/pageSize + 1);

        model.addAttribute("item", new Item());
        model.addAttribute("itemList", itemService.itemListPerPage(pageId, pageSize));
        model.addAttribute("categoryList", categoryService.listCategories());

        int id = userService.getAuthorizedUserId();

        if (id!=0 && userService.getById(id).isAuth()) {
            model.addAttribute("id", id);
            model.addAttribute("role", userService.getById(id).getRole());
        }

        else{
            Map<Item, Integer> itemMap = new HashMap<>();
            if(session.getAttribute("basket")==null)
            session.setAttribute("basket", itemMap);
        }

        return "catalog";
    }

    /**
     * Get mapping for all items
     * @param model model
     * @return page with all items
     */
    @GetMapping("/items")
    public String itemList2(Model model){

        model.addAttribute("it", new Item());
        model.addAttribute("itemList", itemService.itemList());
        model.addAttribute("params", new Param());

        model.addAttribute("authors", paramService.getAllAuthors());
        model.addAttribute("countries", paramService.getAllCountries());
        model.addAttribute("category", "ALL");

        int id = userService.getAuthorizedUserId();
        if (id!=0 && userService.getById(id).isAuth()) {
            model.addAttribute("id", id);
            model.addAttribute("role", userService.getById(id).getRole());
        }

        return "itemList";
    }

    /**
     * Get mapping for items filtered by params
     * @param params params for filtering
     * @param category item's category
     * @param model model
     * @return page with filtered items
     */
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

        int id = userService.getAuthorizedUserId();
        if (id!=0 && userService.getById(id).isAuth()) {

            model.addAttribute("id", id);
            model.addAttribute("role", userService.getById(id).getRole());
        }

        return "itemList";
    }
}
