package com.online_market.controller;

import com.online_market.service.ItemService;
import com.online_market.service.OrderService;
import com.online_market.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    ItemService itemService;

    @Autowired
    UserService userService;


    @GetMapping("/orderHistory")
    public String orderHistory(Model model){

        int id = userService.getAuthirizedUserId();
        if (userService.getById(id).isAuth()) {


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
}
