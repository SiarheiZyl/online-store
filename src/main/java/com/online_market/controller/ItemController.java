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

import java.util.Arrays;
import java.util.List;

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


/*    @GetMapping("items")
    public String itemList(Model model){

        model.addAttribute("item", new Item());
        model.addAttribute("itemList", itemService.itemList());

        return "itemList";
    }*/

    @GetMapping("/catalog")
    public String catalog(Model model){
        int id = userService.getAuthirizedUserId();
        if (userService.getById(id).isAuth()) {
            model.addAttribute("id", id);
            model.addAttribute("user", userService.getById(id));
            model.addAttribute("item", new Item());
            model.addAttribute("itemList", itemService.itemList());
            model.addAttribute("categoryList", categoryService.listCategories());

            //fix: userBucket
            Order bucket = orderService.getBucketOrder(id);


            return "catalog";
        }

        else{
            return "redirect:/";
        }
    }

    @GetMapping("/items")
    public String itemList2(Model model){
        int id = userService.getAuthirizedUserId();
        if (userService.getById(id).isAuth()) {
            model.addAttribute("id", id);
            model.addAttribute("user", userService.getById(id));
            model.addAttribute("item", new Item());
            model.addAttribute("itemList", itemService.itemList());
            model.addAttribute("params", new Param());

            model.addAttribute("authors", paramService.getAllAuthors());
            model.addAttribute("countries", paramService.getAllCountries());
            model.addAttribute("category", "ALL");

            //fix: userBucket
            Order bucket = orderService.getBucketOrder(id);


            return "itemList";
        }

        else{
            return "redirect:/";
        }
    }

    @GetMapping("/filterItems/{category}")
    public String filteredItemList2( @ModelAttribute("params") Param params,@PathVariable("category") String category, Model model){
        int id = userService.getAuthirizedUserId();
        if (userService.getById(id).isAuth()) {

            model.addAttribute("id", id);
            model.addAttribute("user", userService.getById(id));
            model.addAttribute("item", new Item());
            List<Item> itemList;
            if(params.getWidth()==0&&params.getHeight()==0&&params.getAuthor()==null&&params.getCountry()==null)
                itemList = itemService.itemList();
            else
                itemList = itemService.getFilteredItemsByAllParams(params.getAuthor(), params.getCountry(), params.getWidth(), params.getHeight());
            model.addAttribute("itemList", itemService.getFilteredItemsByCategory(itemList, category));

            model.addAttribute("authors", paramService.getAllAuthors());
            model.addAttribute("countries", paramService.getAllCountries());

            //fix: userBucket
            Order bucket = orderService.getBucketOrder(id);


            return "itemList";
        }

        else{
            return "redirect:/";
        }
    }



    @GetMapping("/bucket")
    public String addItem(Model model){
        int id = userService.getAuthirizedUserId();
        if (userService.getById(id).isAuth()) {
            Order order = orderService.getBucketOrder(id) == null ? new Order() : orderService.getBucketOrder(id);

            model.addAttribute("order", order);

            List<PaymentMethod> list = Arrays.asList(PaymentMethod.values());
            List<DeliveryMethod> list2 = Arrays.asList(DeliveryMethod.values());

            model.addAttribute("paymentList", list);
            model.addAttribute("deliveryList", list2);

            model.addAttribute("it", new Item());

            model.addAttribute("id", id);
            model.addAttribute("user", userService.getById(id));

            model.addAttribute("itemMap", itemService.getOrderNotNullItems(order.getOrderId()));

            return "bucket";
        }

        else{
            return "redirect:/";
        }
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
    public String deletItemFromBucket( @PathVariable("itemId") int itemId, @PathVariable("quantity") int quantity){
        int id = userService.getAuthirizedUserId();
        orderService.removeFromBucket(itemId, id, quantity);

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
    public String addItemToOrderProcess( @PathVariable("itemId") int itemId, @ModelAttribute("item") Item item){
        int id = userService.getAuthirizedUserId();
        orderService.addToBucket(item, id);

        return "redirect:/filterItems/"+itemService.getById(itemId).getCategory().getCategoryName();
    }



}
