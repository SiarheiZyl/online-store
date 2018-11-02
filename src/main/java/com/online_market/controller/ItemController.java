package com.online_market.controller;


import com.online_market.dao.ItemDao;
import com.online_market.entity.Item;
import com.online_market.entity.Order;
import com.online_market.entity.enums.DeliveryMethod;
import com.online_market.entity.enums.PaymentMethod;
import com.online_market.service.ItemService;
import com.online_market.service.OrderService;
import com.online_market.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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


    @GetMapping("items")
    public String itemList(Model model){

        model.addAttribute("item", new Item());
        model.addAttribute("itemList", itemService.itemList());

        return "itemList";
    }

    @GetMapping("/user/{id}/items")
    public String itemList2(@PathVariable("id") int id, Model model){

        if (userService.getById(id).isAuth()) {
            model.addAttribute("id", id);
            model.addAttribute("item", new Item());
            model.addAttribute("itemList", itemService.itemList());

            //fix: userBucket
            Order bucket = orderService.getBucketOrder(id);


            return "itemList";
        }

        else{
            return "redirect:/";
        }
    }

    @GetMapping("/user/{id}/bucket")
    public String addItem(@PathVariable("id") int id, Model model){
        if (userService.getById(id).isAuth()) {
            Order order = orderService.getBucketOrder(id) == null ? new Order() : orderService.getBucketOrder(id);

            model.addAttribute("order", order);

            List<PaymentMethod> list = Arrays.asList(PaymentMethod.values());
            List<DeliveryMethod> list2 = Arrays.asList(DeliveryMethod.values());

            model.addAttribute("paymentList", list);
            model.addAttribute("deliveryList", list2);

            model.addAttribute("it", new Item());

            model.addAttribute("id", id);

            model.addAttribute("itemMap", itemService.getBucketItems(order.getOrderId()));

            return "bucket";
        }

        else{
            return "redirect:/";
        }
    }

/*    @PostMapping("/user/{id}/bucket/deleteProcess/{itemId}")
    public String deletItemFromBucket(@PathVariable("id") int id, @PathVariable("itemId") int itemId, Model model){

        orderService.removeFromBucket(itemId, id);

        return "redirect:/user/"+id+"bucket";
    }*/

    @PostMapping("/user/{id}/orderProcess")
    public String addItemProcess(@PathVariable("id") int id, @ModelAttribute("order") Order order){

        if(order.getPaymentMethod()== null || order.getDeliveryMethod()==null)
            return "redirect+/user/"+id+"/bucket";

        orderService.saveBucketToOrders(order, id);


        return "redirect:/user/"+id+"/items";
    }

    @PostMapping("/user/{id}/items/{itemId}/addItemToOrderProcess")
    public String addItemToOrderProcess(@PathVariable("id") int id, @PathVariable("itemId") int itemId, @ModelAttribute("item") Item item, Model model){

        orderService.addToBucket(item, id);

        //userService.test();

        return "redirect:/user/"+id+"/items";
    }



}
