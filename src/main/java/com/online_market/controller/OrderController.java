package com.online_market.controller;

import com.online_market.entity.Order;
import com.online_market.service.ItemService;
import com.online_market.service.OrderService;
import com.online_market.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Class for mapping all paths associated with orders{@link Order}
 *
 * @author Siarhei
 * @version 1.0
 */
@Controller
@RequestMapping("/")
public class OrderController {

    final static Logger logger = Logger.getLogger(OrderController.class);

    @Autowired
    OrderService orderService;

    @Autowired
    ItemService itemService;

    @Autowired
    UserService userService;

    /**
     * Get mapping for page with order history of authorized user
     *
     * @param model model
     * @return page with order history
     */
    @GetMapping("/orderHistory")
    public String orderHistory(Model model) {

        int id = userService.getAuthorizedUserId();
        if (userService.getById(id).isAuth()) {


            model.addAttribute("orders", orderService.getHistoryOfOrders(id));

            model.addAttribute("ord", new Order());
            model.addAttribute("id", id);
            model.addAttribute("user", userService.getById(id));

            return "orderHistory";
        } else {
            return "redirect:/";
        }
    }

    /**
     * Post mapping for repeating previous order
     *
     * @param orderId order id
     * @return orderHistory page
     */
    @PostMapping("/repeatOrderProcess/{orderId}")
    public String repeatOrderItemProcess(@PathVariable("orderId") int orderId) {

        int id = userService.getAuthorizedUserId();

        return "redirect:/orderHistory";
    }
}
