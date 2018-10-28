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


        List<Order> orders = orderService.userOrderList(id);
        Order order;
        if(orders.size()==0)
            order = new Order();
        else
            order = orders.get(0);

        order.setUser(userService.getById(id));

        //model.addAttribute("order", order);

        return "itemList";
    }

    @GetMapping("/user/{id}/bucket")
    public String addItem(@PathVariable("id") int id, Model model){
        Order order = orderService.userOrderList(id) == null ? new Order() : orderService.userOrderList(id).get(0);

        model.addAttribute("order", order);

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

        return "bucket";
    }

    @PostMapping("/user/{id}/orderProcess")
    public String addItemProcess(@PathVariable("id") int id, @ModelAttribute("order") Order order){

        //order.setUser(userService.getById(id));
//        List<Item> itemList;
//        if(order.getItems()!=null)
//            itemList = new ArrayList<>(order.getItems());
//        else
//            itemList = new ArrayList<>();
//
//        itemList.add(itemService.getById(itemId));
//
//        order.setItems(itemList);

//        orderService.save(order);
        order.setUser(userService.getById(id));

        Order order1 = orderService.userOrderList(id).get(0);

        order1.setDeliveryMethod(order.getDeliveryMethod());
        order1.setPaymentMethod(order.getPaymentMethod());

        orderService.update(order1);


        return "redirect:/user/"+id+"/items";
    }

    @PostMapping("/user/{id}/items/{itemId}/addItemToOrderProcess")
    public String addItemToOrderProcess(@PathVariable("id") int id, @PathVariable("itemId") int itemId, @ModelAttribute("item") Item item, Model model){

        List<Order> orders = orderService.userOrderList(id);
        Order order;

        if(orders.size() == 0)
            order = new Order();
        else
            order = orders.get(0);


        List<Item> itemList;
        if(order.getItems()!=null)
            itemList = new ArrayList<>(order.getItems());
        else
            itemList = new ArrayList<>();

        itemList.add(item);
        order.setItems(itemList);

        order.setUser(userService.getById(id));

        orderService.update(order);

        return "redirect:/user/"+id+"/items";
    }



}
