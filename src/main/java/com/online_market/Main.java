package com.online_market;

import com.online_market.config.SpringConfig;
import com.online_market.config.WebConfig;
import com.online_market.dao.OrderDao;
import com.online_market.service.OrderService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext configApplicationContext = new AnnotationConfigApplicationContext( SpringConfig.class);

        OrderService orderService = configApplicationContext.getBean(OrderService.class);

        OrderDao orderDao = configApplicationContext.getBean(OrderDao.class);

        System.out.println("hello");
       // System.out.println(orderDao.orderedItemQuantity(30,3));
    }
}
