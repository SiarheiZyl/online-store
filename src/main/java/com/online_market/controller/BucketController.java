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

    /**
     * Apache log4j object is used to log all important info
     */
    final static Logger logger = Logger.getLogger(BucketController.class);

    /**
     * User service object. See {@link com.online_market.service.UserServiceImpl}
     */
    private final UserService userService;

    /**
     * Order service object. See {@link com.online_market.service.OrderServiceImpl}
     */
    private final OrderService orderService;

    /**
     * Item service object. See {@link com.online_market.service.ItemServiceImpl}
     */
    private final ItemService itemService;

    /**
     * Injecting constructor
     *
     * @param userService  user service
     * @param orderService order service
     * @param itemService  item service
     */
    @Autowired
    public BucketController(OrderService orderService, ItemService itemService, UserService userService) {
        this.orderService = orderService;
        this.itemService = itemService;
        this.userService = userService;
    }

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

            logger.info("User with id: " + id + "visited bucket page.");

            Order bucket = orderService.getBucketOrder(id);

            Order order = orderService.getBucketOrder(id) == null ? new Order() : orderService.getBucketOrder(id);

            model.addAttribute("order", order);

            model.addAttribute("id", id);
            model.addAttribute("role", userService.getById(id).getRole());

            model.addAttribute("itemMap", itemService.getOrderNotNullItems(order.getOrderId()));
            model.addAttribute("numberOfItemsInBucket", itemService.getOrderSize(itemService.getOrderNotNullItems(orderService.getBucketOrder(id).getOrderId())));
        } else {
            logger.info("Unauthorized visited bucket page.");

            model.addAttribute("order", new Order());
            model.addAttribute("itemMap", session.getAttribute("basket"));
            model.addAttribute("numberOfItemsInBucket", itemService.getOrderSize((Map<Item, Integer>) session.getAttribute("basket")));
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

        Map<Item, Integer> map = id == 0 ? (Map<Item, Integer>) session.getAttribute("basket") : itemService.getOrderNotNullItems(orderService.getBucketOrder(id).getOrderId());

        Item item = itemService.getById(itemId);

        if (map.size() != 0 && map.containsKey(item) && item.getAvailableCount() - map.get(item) == 0)
            return -1 + "";
        if (id != 0) {
            logger.info("User with id: " + id + "trying to add item to bucket.");

            orderService.addToBucket(itemId, id);
        } else {
            logger.info("Unauthorized user trying to add item to bucket.");

            orderService.addItemToSession(itemId, session);
        }

        return item.getAvailableCount() + "";
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
            logger.info("User with id: " + id + "trying to remove item from bucket.");

            orderService.updateQuantity(id, itemId, 0);
        } else {
            logger.info("Unauthorized user trying  to remove item from  bucket.");

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
    public String addBucketToOrders(@ModelAttribute("order") Order order, HttpSession session) {

        int id = userService.getAuthorizedUserId();
        if (order.getPaymentMethod() == null || order.getDeliveryMethod() == null)
            return "redirect:/bucket";

        logger.info("User with id: " + id + "trying to save bucket to orders.");

        orderService.saveBucketToOrders(order, id);

        return "redirect:/bucket";
    }
}
