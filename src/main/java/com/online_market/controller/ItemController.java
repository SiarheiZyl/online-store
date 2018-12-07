package com.online_market.controller;


import com.online_market.entity.Item;
import com.online_market.entity.Param;
import com.online_market.entity.enums.Roles;
import com.online_market.service.*;
import com.online_market.utils.ImageUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class for mapping all paths associated with items{@link Item}
 *
 * @author Siarhei
 * @version 1.0
 */
@Controller
public class ItemController {

    final static Logger logger = Logger.getLogger(ItemController.class);

    private final ItemService itemService;

    private final UserService userService;

    private final OrderService orderService;

    private final ParamService paramService;

    private final CategoryService categoryService;

    /**
     * Injecting constructor
     *
     * @param itemService     item service
     * @param userService     user service
     * @param orderService    order service
     * @param paramService    param service
     * @param categoryService category service
     */
    @Autowired
    public ItemController(ItemService itemService, UserService userService, OrderService orderService, ParamService paramService, CategoryService categoryService) {
        this.itemService = itemService;
        this.userService = userService;
        this.orderService = orderService;
        this.paramService = paramService;
        this.categoryService = categoryService;
    }

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
            orderService.updateBucket(id);
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

        if (id != 0 && userService.getById(id).getRole() == Roles.ADMIN)
            model.addAttribute("itemList", itemService.itemListPerPage(pageId, pageSize));
        else
            model.addAttribute("itemList", itemService.visibleItemListPerPage(pageId, pageSize));

        model.addAttribute("categoryList", categoryService.getAllItemsWithIsShown(true));

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

        int id = userService.getAuthorizedUserId();

        model.addAttribute("it", new Item());
        if (id != 0 && userService.getById(id).getRole() == Roles.ADMIN)
            model.addAttribute("itemList", itemService.itemList());
        else
            model.addAttribute("itemList", itemService.visibleItemList());
        model.addAttribute("params", new Param());

        model.addAttribute("authors", paramService.getAllAuthors());
        model.addAttribute("countries", paramService.getAllCountries());
        model.addAttribute("category", "ALL");


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

        int id = userService.getAuthorizedUserId();

        if (!categoryService.findByName(category).isShown())
            return "pageNotFound";

        model.addAttribute("item", new Item());
        List<Item> itemList;
        if (params.getWidth() == 0 && params.getHeight() == 0 && params.getAuthor() == null && params.getCountry() == null)
            itemList = itemService.itemList();
        else
            itemList = itemService.getFilteredItemsByAllParams(params.getAuthor(), params.getCountry(), params.getWidth(), params.getHeight());

        if (id != 0 && userService.getById(id).getRole() == Roles.ADMIN)
            model.addAttribute("itemList", itemService.getFilteredItemsByCategory(itemList, category));
        else
            model.addAttribute("itemList", itemService.getFilteredShownItemsByCategory(itemList, category));

        model.addAttribute("authors", paramService.getAllAuthors());
        model.addAttribute("countries", paramService.getAllCountries());


        if (id != 0 && userService.getById(id).isAuth()) {

            model.addAttribute("role", userService.getById(id).getRole());
            model.addAttribute("id", id);
            model.addAttribute("numberOfItemsInBucket", itemService.getOrderSize(itemService.getOrderNotNullItems(orderService.getBucketOrder(id).getOrderId())));
        } else {
            model.addAttribute("numberOfItemsInBucket", itemService.getOrderSize((Map<Item, Integer>) session.getAttribute("basket")));
        }

        return "itemList";
    }

    @RequestMapping(value = "/image/{itemId}")
    public @ResponseBody
    byte[] getImage(@PathVariable(value = "itemId") String itemId) throws IOException {

        File serverFile = new File(ImageUtil.getImagesDirectoryAbsolutePath() + itemId);

        return Files.readAllBytes(serverFile.toPath());
    }

    @RequestMapping(value = "/smallImage/{itemId}")
    public @ResponseBody
    byte[] getSmallImage(@PathVariable(value = "itemId") String itemId) throws IOException {

        File serverFile = new File(ImageUtil.getImagesDirectoryAbsolutePath() + itemId + "small");

        return Files.readAllBytes(serverFile.toPath());
    }

    @RequestMapping(value = "/mediumImage/{itemId}")
    public @ResponseBody
    byte[] getMediumImage(@PathVariable(value = "itemId") String itemId) throws IOException {

        File serverFile = new File(ImageUtil.getImagesDirectoryAbsolutePath() + itemId + "medium");

        return Files.readAllBytes(serverFile.toPath());
    }

    /**
     * Get mapping for editing item
     *
     * @param itemId item id
     * @param model  model
     * @return item's page
     */
    @GetMapping("/item/{itemId}")
    public String getEditItemPage(@PathVariable int itemId, Model model) {

        int id = userService.getAuthorizedUserId();
        model.addAttribute("id", id);

        if (id != 0) {
            model.addAttribute("role", userService.getById(id).getRole());
            model.addAttribute("numberOfItemsInBucket", itemService.getOrderSize(itemService.getOrderNotNullItems(orderService.getBucketOrder(id).getOrderId())));
        }

        model.addAttribute("item", itemService.getById(itemId));
        model.addAttribute("listCategories", categoryService.listCategories());

        return "editItem";
    }

    @GetMapping("/search")
    public String getSearchPage(Model model, @RequestParam(value = "searchData", required = false) String searchData) {

        int id = userService.getAuthorizedUserId();
        model.addAttribute("id", id);

        if (id != 0) {
            model.addAttribute("role", userService.getById(id).getRole());
            model.addAttribute("numberOfItemsInBucket", itemService.getOrderSize(itemService.getOrderNotNullItems(orderService.getBucketOrder(id).getOrderId())));
        }

        if (searchData == null) {
            model.addAttribute("items", null);
        } else {
            model.addAttribute("items", itemService.search(searchData));
        }

        return "search";
    }
}
