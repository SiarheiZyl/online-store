package com.online_market.controller;

import com.online_market.entity.Order;
import com.online_market.service.ItemService;
import com.online_market.service.OrderService;
import com.online_market.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

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

    private final OrderService orderService;

    private final ItemService itemService;

    private final UserService userService;

    /**
     * Injecting constructor
     * @param userService user service
     * @param orderService order service
     * @param itemService item service
     */
    @Autowired
    public OrderController(OrderService orderService, ItemService itemService, UserService userService) {
        this.orderService = orderService;
        this.itemService = itemService;
        this.userService = userService;
    }

    /**
     * Get mapping for page with order history of authorized user
     *
     * @param model model
     * @return page with order history
     */
    @GetMapping("/orderHistory/{pageId}")
    public String orderHistory(@PathVariable("pageId") int pageId, Model model, @RequestParam(value = "fromDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate, @RequestParam(value = "toDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {

        int id = userService.getAuthorizedUserId();
        if (id!=0) {

            int pageSize = 10;
            long totalPages ;

            model.addAttribute("pageId", pageId);


            if (fromDate == null && toDate == null) {

                totalPages= orderService.getHistoryOfOrders(id).size()/pageSize + 1;

                if (pageId > pageSize)
                    return "pageNotFound";

                model.addAttribute("orders", orderService.getHistoryOfOrdersPerPage(id, pageId, pageSize));
                model.addAttribute("fromDate", null);
                model.addAttribute("toDate", null);
            }
            else {
                totalPages = orderService.sizeOfHistoryOfOrdersFilteredByDate(id, fromDate, toDate) / pageSize + 1;

                if (pageId > pageSize)
                    return "pageNotFound";

                model.addAttribute("orders", orderService.getHistoryOfOrdersPerPageFilteredFromToDate(id,pageId, pageSize, fromDate, toDate));

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                model.addAttribute("fromDate", format.format(fromDate));
                model.addAttribute("toDate", format.format(toDate));
            }
            model.addAttribute("pageSize", totalPages);

            model.addAttribute("ord", new Order());
            model.addAttribute("id", id);
            model.addAttribute("role", userService.getById(id).getRole());
            model.addAttribute("numberOfItemsInBucket", itemService.getOrderSize(itemService.getOrderNotNullItems(orderService.getBucketOrder(id).getOrderId())));

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
