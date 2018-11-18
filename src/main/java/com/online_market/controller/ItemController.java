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
 *
 * @author Siarhei
 * @version 1.0
 */
@Controller
public class ItemController {

    final static Logger logger = Logger.getLogger(ItemController.class);

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ParamService paramService;

    @Autowired
    private CategoryService categoryService;

    /**
     * Get mapping for catalog
     *
     * @param model   model
     * @param session HttpSession
     * @return catalog page
     */
    @GetMapping("/catalog/{pageId}")
    public String catalog(@PathVariable("pageId") int pageId, Model model, HttpSession session) {


        int pageSize = 12;

        int id = userService.getAuthorizedUserId();

        if (id != 0 && userService.getById(id).isAuth()) {

            Map<Item, Integer> itemMap = (Map<Item, Integer>) session.getAttribute("basket");
            if (itemMap != null) {
                orderService.addFromSessionToBucket(itemMap, id);
                session.invalidate();
            }

            model.addAttribute("id", id);
            model.addAttribute("role", userService.getById(id).getRole());
            model.addAttribute("numberOfItemsInBucket", itemService.getOrderSize(itemService.getOrderNotNullItems(orderService.getBucketOrder(id).getOrderId())));
        } else {
            Map<Item, Integer> itemMap = new HashMap<>();
            if (session.getAttribute("basket") == null)
                session.setAttribute("basket", itemMap);
            model.addAttribute("numberOfItemsInBucket", itemService.getOrderSize((Map<Item, Integer>) session.getAttribute("basket")));
        }

        int totalPages = itemService.itemList().size() / pageSize + 1;

        if (pageId > totalPages)
            return "pageNotFound";

        model.addAttribute("pageId", pageId);
        model.addAttribute("pageSize", totalPages);

        model.addAttribute("item", new Item());
        model.addAttribute("itemList", itemService.itemListPerPage(pageId, pageSize));
        model.addAttribute("categoryList", categoryService.listCategories());


        return "catalog";
    }

    /**
     * Get mapping for all items
     *
     * @param model model
     * @return page with all items
     */
    @GetMapping("/items")
    public String itemList2(Model model, HttpSession session) {

        model.addAttribute("it", new Item());
        model.addAttribute("itemList", itemService.itemList());
        model.addAttribute("params", new Param());

        model.addAttribute("authors", paramService.getAllAuthors());
        model.addAttribute("countries", paramService.getAllCountries());
        model.addAttribute("category", "ALL");

        int id = userService.getAuthorizedUserId();
        if (id != 0 && userService.getById(id).isAuth()) {
            model.addAttribute("id", id);
            model.addAttribute("role", userService.getById(id).getRole());
            model.addAttribute("numberOfItemsInBucket", itemService.getOrderSize(itemService.getOrderNotNullItems(orderService.getBucketOrder(id).getOrderId())));
        } else {
            model.addAttribute("numberOfItemsInBucket", itemService.getOrderSize((Map<Item, Integer>) session.getAttribute("basket")));
        }

        return "itemList";
    }

    /**
     * Get mapping for items filtered by params
     *
     * @param params   params for filtering
     * @param category item's category
     * @param model    model
     * @return page with filtered items
     */
    @GetMapping("/filterItems/{category}")
    public String filteredItemList2(@ModelAttribute("params") Param params, @PathVariable("category") String category, Model model, HttpSession session) {

        model.addAttribute("item", new Item());
        List<Item> itemList;
        if (params.getWidth() == 0 && params.getHeight() == 0 && params.getAuthor() == null && params.getCountry() == null)
            itemList = itemService.itemList();
        else
            itemList = itemService.getFilteredItemsByAllParams(params.getAuthor(), params.getCountry(), params.getWidth(), params.getHeight());

        model.addAttribute("itemList", itemService.getFilteredItemsByCategory(itemList, category));

        model.addAttribute("authors", paramService.getAllAuthors());
        model.addAttribute("countries", paramService.getAllCountries());

        int id = userService.getAuthorizedUserId();
        if (id != 0 && userService.getById(id).isAuth()) {

            model.addAttribute("role", userService.getById(id).getRole());
            model.addAttribute("id", id);
            model.addAttribute("numberOfItemsInBucket", itemService.getOrderSize(itemService.getOrderNotNullItems(orderService.getBucketOrder(id).getOrderId())));
        } else {
            model.addAttribute("numberOfItemsInBucket", itemService.getOrderSize((Map<Item, Integer>) session.getAttribute("basket")));
        }

        return "itemList";
    }
}
