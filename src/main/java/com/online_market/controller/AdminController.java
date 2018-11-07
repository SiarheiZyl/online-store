package com.online_market.controller;


import com.online_market.entity.Order;
        import com.online_market.entity.enums.DeliveryMethod;
        import com.online_market.entity.enums.OrderStatus;
        import com.online_market.entity.enums.PaymentMethod;
        import com.online_market.entity.enums.PaymentStatus;
        import com.online_market.service.OrderService;
        import com.online_market.service.UserService;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.web.bind.annotation.*;

        import java.util.Arrays;
        import java.util.List;

@Controller
@RequestMapping("/")
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    OrderService orderService;

    @GetMapping("/user/{id}/editOrders")
    public String getEditOrdersPage(@PathVariable("id") int id, Model model) {

            model.addAttribute("orders", orderService.getAllTrackedOrders());
            model.addAttribute("order", new Order());
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

    @GetMapping("/user/{id}/statistics")
    public String getStatisticsPage(@PathVariable("id") int id, Model model) {

            model.addAttribute("id", id);
            model.addAttribute("user", userService.getById(id));

            model.addAttribute("topUsers", orderService.getTopUsers());
            model.addAttribute("topItems", orderService.getTopItems());
            model.addAttribute("incomeMap", orderService.getIncome());

            return "statisticsForAdmin";
    }

    @PostMapping("/user/{id}/editOrdersProcess/{orderId}")
    public  String editOrders(@PathVariable("id") int id, @PathVariable("orderId") int orderId, @ModelAttribute("order") Order order){

        Order order1 = orderService.getById(orderId);
        order1.setOrderStatus(order.getOrderStatus());
        order1.setPaymentStatus(order.getPaymentStatus());

        orderService.update(order1);

        return "redirect:/user/"+id+"/editOrders" ;
    }
}
