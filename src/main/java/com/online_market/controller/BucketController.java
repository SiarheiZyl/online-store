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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class BucketController {

    @Autowired
    OrderService orderService;

    @Autowired
    ItemService itemService;

    @Autowired
    UserService userService;

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

    @PostMapping("/bucket/deleteProcess/{itemId}/{quantity}")
    public String deleteItemFromBucket(@PathVariable("itemId") int itemId, @PathVariable("quantity") int quantity, HttpSession session){

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

    @GetMapping("/addItemToOrderProcess")
    @ResponseBody
    public String addItemToOrderProcess( @RequestParam("itId") int itemId, HttpSession session){

        int id = userService.getAuthirizedUserId();
        if(id!=0)
            orderService.addToBucket(itemId, id);
        else {
            orderService.addItemToSession(itemId, session);
        }

        return itemService.getById(itemId).getAvailableCount()+"";
    }
}
