package com.online_market.controller;

import com.online_market.entity.Item;
import com.online_market.entity.Order;
import com.online_market.entity.enums.*;
import com.online_market.service.CategoryService;
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
import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * Class for mapping all admin paths
 *
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
     *
     * @param model model
     * @return page for editing orders
     */
    @GetMapping("/editOrders/{pageId}")
    public String getEditOrdersPage(@PathVariable int pageId, Model model, @RequestParam(value = "fromDate", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date fromDate, @RequestParam(value = "toDate", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date toDate) {

        int id = userService.getAuthorizedUserId();

        if (id != 0 && userService.getById(id).getRole() == Roles.ADMIN) {

            int pageSize = 10;

            model.addAttribute("pageId", pageId);

            if(fromDate==null && toDate ==null) {
                model.addAttribute("orders", orderService.getOrdersPerPage(pageId, pageSize));
                model.addAttribute("pageSize", orderService.sizeOfTrackedOrders() / pageSize + 1);
                model.addAttribute("fromDate", null);
                model.addAttribute("toDate", null);
            }
            else {
                model.addAttribute("pageSize", orderService.sizeOfTrackedOrdersFilteredByDate(fromDate, toDate) / pageSize + 1);
                model.addAttribute("orders", orderService.getOrdersPerPageFilteredFromToDate(pageId, pageSize, fromDate, toDate));

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                model.addAttribute("fromDate", format.format(fromDate));
                model.addAttribute("toDate", format.format(toDate));
            }




            model.addAttribute("id", id);
            model.addAttribute("login", userService.getById(id).getLogin());
            model.addAttribute("role", userService.getById(id).getRole());

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
     *
     * @param model model
     * @return page with statistics of top items, users and income of the store
     */
    @GetMapping("/statistics")
    public String getStatisticsPage(Model model) {

        int id = userService.getAuthorizedUserId();

        if (id != 0 && userService.getById(id).getRole() == Roles.ADMIN) {
            model.addAttribute("id", id);
            model.addAttribute("role", userService.getById(id).getRole());

            model.addAttribute("topUsers", orderService.getTopUsers());
            model.addAttribute("topItems", orderService.getTopItems());
            model.addAttribute("incomeMap", orderService.getIncome());

            return "statisticsForAdmin";
        } else {
            return "redirect:/login";
        }
    }

    /**
     * Get mapping for editing item
     *
     * @param itemId item id
     * @param model  model
     * @return
     */
    @GetMapping("/editItem/{itemId}")
    public String getEditItemPage(@PathVariable int itemId, Model model) {

        int id = userService.getAuthorizedUserId();

        if (id != 0 && userService.getById(id).getRole() == Roles.ADMIN) {
            model.addAttribute("id", id);
            model.addAttribute("role", userService.getById(id).getRole());

            model.addAttribute("item", itemService.getById(itemId));
            model.addAttribute("listCategories", categoryService.listCategories());

            return "editItem";
        } else {
            return "redirect:/login";
        }
    }


    /**
     * Post mapping for adding new item
     *
     * @param itemName       item name
     * @param category       category
     * @param author         author
     * @param country        country
     * @param height         height
     * @param width          width
     * @param availableCount availible count
     * @param price          price
     */
    @PostMapping("/editItemProcess")
    @ResponseBody
    public void updateItem(@RequestParam("itemId") int itemId, @RequestParam("itemName") String itemName, @RequestParam("category") String category, @RequestParam("author") String author, @RequestParam("country") String country, @RequestParam("height") int height, @RequestParam("width") int width, @RequestParam("availableCount") int availableCount, @RequestParam("price") int price) {

        itemService.update(itemId, itemName, category, author, country, height, width, availableCount, price);
    }

    /**
     * Post mapping for changing order and payment status
     *
     * @param orderId       orderId
     * @param orderStatus   orderStatus
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
     *
     * @param model model
     * @return page for adding new category and new items
     */
    @GetMapping("/editCategories")
    public String getEditCategories(Model model) {

        int id = userService.getAuthorizedUserId();

        if (id != 0 && userService.getById(id).getRole() == Roles.ADMIN) {
            model.addAttribute("id", id);
            model.addAttribute("role", userService.getById(id).getRole());

            model.addAttribute("topUsers", orderService.getTopUsers());
            model.addAttribute("topItems", orderService.getTopItems());
            model.addAttribute("incomeMap", orderService.getIncome());

            model.addAttribute("listCategories", categoryService.listCategories());

            return "editCategories";
        } else {
            return "redirect:/login";
        }
    }

/*    @PostMapping("/filterOrdersProcess")
    @ResponseBody
    public String filterOrders(@RequestParam("fromDate") @DateTimeFormat(pattern="yyyy-MM-dd") Date fromDate, @RequestParam("toDate") @DateTimeFormat(pattern="yyyy-MM-dd") Date toDate) {


        return "";
    }*/

    /**
     * Post mapping for adding new category
     *
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
     *
     * @param itemName  item name
     * @param itemCateg category
     * @param author    author
     * @param country   country
     * @param height    height
     * @param width     width
     * @param avalCount availible count
     * @param price     price
     */
    @PostMapping("/addNewItemProcess")
    @ResponseBody
    public void addNewItem(@RequestParam("itemName") String itemName, @RequestParam("itemCateg") String itemCateg, @RequestParam("author") String author, @RequestParam("country") String country, @RequestParam("height") int height, @RequestParam("width") int width, @RequestParam("avalCount") int avalCount, @RequestParam("price") int price) {

        itemService.addNewItem(itemName, avalCount, price, itemCateg, author, country, height, width);
    }
}
