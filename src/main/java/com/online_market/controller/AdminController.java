package com.online_market.controller;


import com.online_market.entity.Order;
import com.online_market.entity.enums.*;
import com.online_market.service.OrderService;
        import com.online_market.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.web.bind.annotation.*;

        import java.util.Arrays;
        import java.util.List;

@Controller
@RequestMapping("/")
public class AdminController {

    final static Logger logger = Logger.getLogger(AdminController.class);

    @Autowired
    UserService userService;

    @Autowired
    OrderService orderService;

    @GetMapping("/editOrders")
    public String getEditOrdersPage( Model model) {

            int id = userService.getAuthirizedUserId();

        if (id != 0 && userService.getById(id).getRole()== Roles.ADMIN) {
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
        }
        else {
            return "redirect:/login";
        }


    }

    @GetMapping("/statistics")
    public String getStatisticsPage(Model model) {

        int id = userService.getAuthirizedUserId();

        if (id != 0 && userService.getById(id).getRole()== Roles.ADMIN) {
            model.addAttribute("id", id);
            model.addAttribute("user", userService.getById(id));

            model.addAttribute("topUsers", orderService.getTopUsers());
            model.addAttribute("topItems", orderService.getTopItems());
            model.addAttribute("incomeMap", orderService.getIncome());

            return "statisticsForAdmin";
        }
        else {
            return "redirect:/login";
        }
    }

    @PostMapping("/editOrdersProcess")
    @ResponseBody
    public int editOrders(@RequestParam("orderId") int orderId, @RequestParam("orderStatus") OrderStatus orderStatus, @RequestParam("paymentStatus") PaymentStatus paymentStatus ){

        Order order1 = orderService.getById(orderId);
        order1.setOrderStatus(orderStatus);
        order1.setPaymentStatus(paymentStatus);

        orderService.update(order1);
        OrderStatus[] orderStatuses = OrderStatus.values();
        for (int i = 0; i<orderStatuses.length; i++) {
            if(order1.getOrderStatus()==orderStatuses[i])
                return i;
        }

        return 0;
    }
}
