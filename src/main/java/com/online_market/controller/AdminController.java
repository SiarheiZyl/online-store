package com.online_market.controller;

import com.online_market.entity.Order;
import com.online_market.entity.enums.*;
import com.online_market.service.CategoryService;
import com.online_market.service.ItemService;
import com.online_market.service.OrderService;
import com.online_market.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


/**
 * Class for mapping all admin paths
 * @author Siarhei
 * @version 1.0
 */
@Controller
@RequestMapping("/")
public class AdminController {

    final static Logger logger = Logger.getLogger(AdminController.class);

    @Autowired
    UserService userService;

    @Autowired
    OrderService orderService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ItemService itemService;

    /**
     * Get mapping for authorized admin
     * @param model model
     * @return page for editing orders
     */
    @GetMapping("/editOrders")
    public String getEditOrdersPage(Model model) {

        int id = userService.getAuthorizedUserId();

        if (id != 0 && userService.getById(id).getRole() == Roles.ADMIN) {
            model.addAttribute("orders", orderService.getAllTrackedOrders());
            model.addAttribute("id", id);
            model.addAttribute("user", userService.getById(id));

            List<PaymentMethod> list = Arrays.asList(PaymentMethod.values());
            List<DeliveryMethod> list2 = Arrays.asList(DeliveryMethod.values());
            List<PaymentStatus> list3 = Arrays.asList(PaymentStatus.values());
            List<OrderStatus> list4 = Arrays.asList(OrderStatus.values());


            model.addAttribute("paymentList", list);
            model.addAttribute("deliveryList", list2);
            model.addAttribute("paymentStatusList", list3);
            model.addAttribute("orderStatusList", list4);

            return "editOrders";
        } else {
            return "redirect:/login";
        }
    }

    /**
     * Get mapping for authorized admin
     * @param model model
     * @return page with statistics of top items, users and income of the store
     */
    @GetMapping("/statistics")
    public String getStatisticsPage(Model model) {

        int id = userService.getAuthorizedUserId();

        if (id != 0 && userService.getById(id).getRole() == Roles.ADMIN) {
            model.addAttribute("id", id);
            model.addAttribute("user", userService.getById(id));

            model.addAttribute("topUsers", orderService.getTopUsers());
            model.addAttribute("topItems", orderService.getTopItems());
            model.addAttribute("incomeMap", orderService.getIncome());

            return "statisticsForAdmin";
        } else {
            return "redirect:/login";
        }
    }

    /**
     * Post mapping for changing order and payment status
     * @param orderId orderId
     * @param orderStatus orderStatus
     * @param paymentStatus paymentStatus
     * @return orderStatus number
     */
    @PostMapping("/editOrdersProcess")
    @ResponseBody
    public int editOrders(@RequestParam("orderId") int orderId, @RequestParam("orderStatus") OrderStatus orderStatus, @RequestParam("paymentStatus") PaymentStatus paymentStatus) {

        Order order1 = orderService.getById(orderId);
        order1.setOrderStatus(orderStatus);
        order1.setPaymentStatus(paymentStatus);

        orderService.update(order1);
        OrderStatus[] orderStatuses = OrderStatus.values();
        for (int i = 0; i < orderStatuses.length; i++) {
            if (order1.getOrderStatus() == orderStatuses[i])
                return i;
        }

        return 0;
    }

    /**
     * Get mapping for authorized admin
     * @param model model
     * @return page for adding new category and new items
     */
    @GetMapping("/editCategories")
    public String getEditCategories(Model model) {

        int id = userService.getAuthorizedUserId();

        if (id != 0 && userService.getById(id).getRole() == Roles.ADMIN) {
            model.addAttribute("id", id);
            model.addAttribute("user", userService.getById(id));

            model.addAttribute("topUsers", orderService.getTopUsers());
            model.addAttribute("topItems", orderService.getTopItems());
            model.addAttribute("incomeMap", orderService.getIncome());

            model.addAttribute("listCategories", categoryService.listCategories());

            return "editCategories";
        } else {
            return "redirect:/login";
        }
    }

    /**
     * Post mapping for adding new category
     * @param categName category name
     * @return category name
     */
    @PostMapping("/addNewCategoryProcess")
    @ResponseBody
    public String addNewCateg(@RequestParam("categName") String categName) {

        String categoryName = categoryService.save(categName);

        return categoryName;
    }


    /**
     * Post mapping for adding new item
     * @param itemName item name
     * @param itemCateg category
     * @param author author
     * @param country country
     * @param height height
     * @param width width
     * @param avalCount availible count
     * @param price price
     */
    @PostMapping("/addNewItemProcess")
    @ResponseBody
    public void  addNewItem(@RequestParam("itemName") String itemName, @RequestParam("itemCateg") String itemCateg, @RequestParam("author") String author, @RequestParam("country") String country, @RequestParam("height") int height, @RequestParam("width") int width, @RequestParam("avalCount") int avalCount, @RequestParam("price") int price) {

        itemService.addNewItem(itemName, avalCount, price, itemCateg, author, country, height, width);
    }
}
