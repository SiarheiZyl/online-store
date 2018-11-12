package com.online_market.controller;


import com.online_market.entity.Item;
import com.online_market.entity.Order;
import com.online_market.entity.enums.DeliveryMethod;
import com.online_market.entity.enums.PaymentMethod;
import com.online_market.service.ItemService;
import com.online_market.service.OrderService;
import com.online_market.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * Class for mapping all paths associated with user bucket
 *
 * @author Siarhei
 * @version 1.0
 */
@Controller
@RequestMapping("/")
public class BucketController {

    final static Logger logger = Logger.getLogger(BucketController.class);

    @Autowired
    OrderService orderService;

    @Autowired
    ItemService itemService;

    @Autowired
    UserService userService;

    /**
     * Get mapping for all users
     *
     * @param model   model
     * @param session HttpSession
     * @return bucket page
     */
    @GetMapping("/bucket")
    public String getBucket(Model model, HttpSession session) {

        List<PaymentMethod> list = Arrays.asList(PaymentMethod.values());
        List<DeliveryMethod> list2 = Arrays.asList(DeliveryMethod.values());

        model.addAttribute("paymentList", list);
        model.addAttribute("deliveryList", list2);

        model.addAttribute("it", new Item());

        int id = userService.getAuthorizedUserId();
        if (id != 0 && userService.getById(id).isAuth()) {
            Order bucket = orderService.getBucketOrder(id);

            Map<Item, Integer> itemMap = (Map<Item, Integer>) session.getAttribute("basket");
            if (itemMap != null) {
                orderService.addFromSessionToBucket(itemMap, id);
                session.invalidate();
            }

            Order order = orderService.getBucketOrder(id) == null ? new Order() : orderService.getBucketOrder(id);

            model.addAttribute("order", order);

            model.addAttribute("id", id);
            model.addAttribute("user", userService.getById(id));

            model.addAttribute("itemMap", itemService.getOrderNotNullItems(order.getOrderId()));
        } else {
            model.addAttribute("order", new Order());
            model.addAttribute("itemMap", (Map<Item, Integer>) session.getAttribute("basket"));
        }

        return "bucket";
    }

    /**
     * Post mapping for adding item to bucket
     *
     * @param itemId  itemId
     * @param session HttpSession
     * @return availible count of added item
     */
    @GetMapping("/addItemToOrderProcess")
    @ResponseBody
    public String addItemToOrder(@RequestParam("itId") int itemId, HttpSession session) {

        int id = userService.getAuthorizedUserId();
        if (id != 0)
            orderService.addToBucket(itemId, id);
        else {
            orderService.addItemToSession(itemId, session);
        }

        return itemService.getById(itemId).getAvailableCount() + "";
    }

    /**
     * Post mapping for deleting item in bucket
     *
     * @param itemId   itemId
     * @param quantity quantity
     * @param session  HttpSession
     */
    @PostMapping("/deleteProcess")
    @ResponseBody
    public void deleteItemFromBucket(@RequestParam("itemId") int itemId, @RequestParam("quantity") int quantity, HttpSession session) {

        int id = userService.getAuthorizedUserId();
        orderService.removeFromBucket(itemId, id, quantity);
        if (id != 0) {
            orderService.updateQuantity(id, itemId, 0);
        } else {
            Map<Item, Integer> itemMap = (Map<Item, Integer>) session.getAttribute("basket");
            itemMap.remove(itemService.getById(itemId));
            session.setAttribute("basket", itemMap);
        }
    }

    /**
     * Post mapping to create an order in bucket
     *
     * @param order created order
     * @return redirecting to the catalog
     */
    @PostMapping("/orderProcess")
    public String addBucketToOrders(@ModelAttribute("order") Order order) {

        int id = userService.getAuthorizedUserId();
        if (order.getPaymentMethod() == null || order.getDeliveryMethod() == null)
            return "redirect:/bucket";

        orderService.saveBucketToOrders(order, id);

        return "redirect:/bucket";
    }
}
