package com.online_market.controller;


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

import java.util.ArrayList;
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

        System.out.println(itemService.itemList().get(0));
        return "itemList";
    }

    @GetMapping("/user/{id}/items")
    public String itemList2(@PathVariable("id") int id, Model model){

        model.addAttribute("item", new Item());
        model.addAttribute("itemList", itemService.itemList());

        return "itemList";
    }

    @GetMapping("/user/{id}/items/{itemId}/add")
    public String addItem(@PathVariable("id") int id, @PathVariable("itemId") int itemId, Model model){
        model.addAttribute("order", new Order());

        List<PaymentMethod> list = new ArrayList<>();
        list.add(PaymentMethod.APPLE_PAY);
        list.add(PaymentMethod.GOOGLE_PAY);
        list.add(PaymentMethod.CREDIT_CARDS);
        list.add(PaymentMethod.MASTERPASS);

        List<DeliveryMethod> list2 = new ArrayList<>();
        list2.add(DeliveryMethod.COURIER);
        list2.add(DeliveryMethod.PICKUP);
        list2.add(DeliveryMethod.POST);

        model.addAttribute("paymentList", list);
        model.addAttribute("deliveryList", list2);

        model.addAttribute("id", id);
        model.addAttribute("itemId", itemId);

        return "addItem";
    }

    @PostMapping("/user/{id}/items/{itemId}/addItemProcess")
    public String addItemProcess(@PathVariable("id") int id, @PathVariable("itemId") int itemId, @ModelAttribute("order") Order order, Model model){
        order.setUser(userService.getById(id));
        List<Item> itemList;
        if(order.getItems()!=null)
            itemList = new ArrayList<>(order.getItems());
        else
            itemList = new ArrayList<>();

        itemList.add(itemService.getById(itemId));

        order.setItems(itemList);

        orderService.save(order);

        return "redirect:../..";
    }



}
