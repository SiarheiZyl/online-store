package com.online_market.controller;


import com.online_market.entity.Item;
import com.online_market.entity.Order;
import com.online_market.entity.Param;
import com.online_market.entity.enums.DeliveryMethod;
import com.online_market.entity.enums.PaymentMethod;
import com.online_market.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ItemController {

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
            Order bucket = orderService.getBucketOrder(id);
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

        model.addAttribute("item", new Item());
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
            Order bucket = orderService.getBucketOrder(id);
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
            Order bucket = orderService.getBucketOrder(id);
        }

        return "itemList";
    }



    @GetMapping("/bucket")
    public String getBucket(Model model, HttpSession session){

        List<PaymentMethod> list = Arrays.asList(PaymentMethod.values());
        List<DeliveryMethod> list2 = Arrays.asList(DeliveryMethod.values());

        model.addAttribute("paymentList", list);
        model.addAttribute("deliveryList", list2);

        model.addAttribute("it", new Item());

        int id = userService.getAuthirizedUserId();
        if (id != 0 && userService.getById(id).isAuth()) {
            Map<Item, Integer> itemMap = (Map<Item, Integer>) session.getAttribute("basket");
            if(itemMap!=null) {
                orderService.addFromSessionToBucket(itemMap, id);
                session.invalidate();
            }

            Order order = orderService.getBucketOrder(id) == null ? new Order() : orderService.getBucketOrder(id);

            model.addAttribute("order", order);

            model.addAttribute("id", id);
            model.addAttribute("user", userService.getById(id));

            model.addAttribute("itemMap", itemService.getOrderNotNullItems(order.getOrderId()));
        }

        else{
            model.addAttribute("order", new Order());
            model.addAttribute("itemMap", (Map<Item, Integer>) session.getAttribute("basket"));
        }

        return "bucket";
    }

    @GetMapping("/orderHistory")
    public String orderHistory(Model model){

        int id = userService.getAuthirizedUserId();
        if (userService.getById(id).isAuth()) {


            model.addAttribute("ord", new Order());

            model.addAttribute("orders", orderService.getHistoryOfOrders(id));

            model.addAttribute("id", id);
            model.addAttribute("user", userService.getById(id));

            return "orderHistory";
        }

        else{
            return "redirect:/";
        }
    }

    @PostMapping("/repeatOrderProcess/{orderId}")
    public String repeatOrderItemProcess( @PathVariable("orderId") int orderId){
        int id = userService.getAuthirizedUserId();

        return "redirect:/orderHistory";
    }

    @PostMapping("/bucket/deleteProcess/{itemId}/{quantity}")
    public String deleteItemFromBucket( @PathVariable("itemId") int itemId, @PathVariable("quantity") int quantity, HttpSession session){

        int id = userService.getAuthirizedUserId();
        orderService.removeFromBucket(itemId, id, quantity);
        if(id != 0) {
            orderService.updateQuantity(id, itemId, 0);
        }
        else {
            Map<Item, Integer> itemMap = (Map<Item, Integer>) session.getAttribute("basket");
            itemMap.remove(itemService.getById(itemId));
            session.setAttribute("basket", itemMap);
        }

        return "redirect:/bucket";
    }

    @PostMapping("/orderProcess")
    public String addItemProcess( @ModelAttribute("order") Order order){

        int id = userService.getAuthirizedUserId();
        if(order.getPaymentMethod()== null || order.getDeliveryMethod()==null)
            return "redirect:/bucket";

        orderService.saveBucketToOrders(order, id);

        return "redirect:/items";
    }

    @PostMapping("/items/{itemId}/addItemToOrderProcess")
    public String addItemToOrderProcess( @PathVariable("itemId") int itemId, HttpSession session){

        int id = userService.getAuthirizedUserId();
        if(id!=0)
            orderService.addToBucket(itemId, id);
        else {
            orderService.addItemToSession(itemId, session);
        }

        return "redirect:/filterItems/"+itemService.getById(itemId).getCategory().getCategoryName();
    }
}
