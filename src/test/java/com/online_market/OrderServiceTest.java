package com.online_market;


import com.online_market.dao.*;
import com.online_market.entity.*;
import com.online_market.entity.enums.DeliveryMethod;
import com.online_market.entity.enums.PaymentMethod;
import com.online_market.service.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Class for testing ${@link com.online_market.service.OrderServiceImpl}
 * @author Siarhei
 * @version 1.0
 */
public class OrderServiceTest {

    @Mock
    private OrderDaoImpl orderDaoMock;

    @Mock
    private ItemDaoImpl itemDaoMock;

    @Mock
    private UserDaoImpl userDaoMock;

    @InjectMocks
    private OrderServiceImpl orderService;

    @InjectMocks
    private UserServiceImpl userService;

    @Before
    public void setUp() throws Exception{

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveOrder_void(){

        orderService.save(new Order());

        verify(orderDaoMock).save(any(Order.class));
    }

    @Test
    public void testGetOrderById_returnsOrder(){

        //expected
        int orderId = 8;
        Order expected = new Order();
        expected.setOrderId(orderId);

        //mock
        when(orderDaoMock.getById(orderId)).thenReturn(expected);

        //actual
        Order actual = orderService.getById(orderId);

        //assert
        assertEquals(expected, actual);
    }

    @Test
    public void testUpdateOrder_void(){

        orderService.update(new Order());

        verify(orderDaoMock).update(any(Order.class));
    }

    @Test
    public void testListOfUserOrders_returnsListOfOrders(){

        //expected
        Order order = new Order();
        Order order1 = new Order();
        List<Order> expected = new ArrayList<>();

        expected.add(order);
        expected.add(order1);

        //mock
        when(orderDaoMock.userOrderList(1)).thenReturn(expected);

        //actual
        List<Order> actual = orderService.userOrderList(1);

        //assert
        assertEquals(expected, actual);
    }

    @Test
    public void testGetUserBucket_returnsBucketOrder(){

        //data
        List<Order> orders = new ArrayList<>();

        Order order = new Order();
        order.setPaymentMethod(PaymentMethod.APPLE_PAY);
        order.setDeliveryMethod(DeliveryMethod.COURIER);

        //expected
        Order expected = new Order();

        orders.add(order);
        orders.add(expected);

        //mock
        when(orderDaoMock.userOrderList(1)).thenReturn(orders);

        //actual
        Order actual = orderService.getBucketOrder(1);

        //assert
        assertEquals(expected, actual);
    }

    @Test
    public void testSaveBucketToOrders_void(){

        int userId = 1;

        when(userDaoMock.getById(userId)).thenReturn(new User());

        orderService.saveBucketToOrders(new Order(), userId);

        verify(itemDaoMock).itemList();

        verify(userDaoMock).getById(userId);

        verify(orderDaoMock).userOrderList(userId);
    }

    @Test
    public void testAddItemToBucket_void(){

        int itemId = 1;
        int avalCount = 5;

        int userId = 1;

        User user = new User();
        user.setId(userId);

        Item item = new Item();
        item.setItemId(itemId);
        item.setAvailableCount(avalCount);

        when(userDaoMock.getById(userId)).thenReturn(user);

        when(itemDaoMock.getById(itemId)).thenReturn(item);

        orderService.addToBucket(itemId, userId);

        verify(itemDaoMock).updateQuantity(item);

        verify(userDaoMock, times(3)).getById(userId);
    }

    @Test
    public void testUpdateQuantity_void(){

        int userId = 1;
        int itemId = 1;
        int orderId = 1;

        int quantity = 2;

        User user = new User();
        user.setId(userId);

        Item item = new Item();
        item.setItemId(itemId);

        Order order = new Order();
        order.setOrderId(orderId);

        List<Order> list = new ArrayList<>();
        list.add(order);

        when(userDaoMock.getById(userId)).thenReturn(user);

        when(itemDaoMock.getById(itemId)).thenReturn(item);

        when(orderDaoMock.userOrderList(userId)).thenReturn(list);

        orderService.updateQuantity(userId, itemId, quantity);

        verify(itemDaoMock).updateQuantityOfOrderedItem(orderId, itemId, quantity);
    }

    @Test
    public void testSetQuantity_void(){

        int itemId = 1;
        int orderId = 1;
        int quantity = 2;


        Item item = new Item();
        item.setItemId(itemId);

        Order order = new Order();
        order.setOrderId(orderId);

        List<Order> list = new ArrayList<>();
        list.add(order);

        when(itemDaoMock.getById(itemId)).thenReturn(item);

        orderService.setQuantity(orderId, itemId, quantity);

        verify(itemDaoMock).updateQuantityOfOrderedItem(orderId, itemId, quantity);
    }

    @Test
    public void testRemovingFromBucket_void() {

        int itemId = 1;
        int userId = 1;
        int quantity = 2;


        Item item = new Item();
        item.setItemId(itemId);


        when(itemDaoMock.getById(itemId)).thenReturn(item);

        orderService.removeFromBucket(itemId, userId, quantity);

        verify(itemDaoMock).updateQuantity(item);
    }

    @Test
    public void testGetAllTrackedOrders_returnsListOfOrder(){

        //data
        Order order1 = new Order();
        order1.setOrderId(1);
        order1.setDeliveryMethod(DeliveryMethod.COURIER);
        order1.setPaymentMethod(PaymentMethod.APPLE_PAY);

        Order order2 = new Order();
        order2.setOrderId(2);
        order2.setDeliveryMethod(DeliveryMethod.PICKUP);
        order2.setPaymentMethod(PaymentMethod.MASTERPASS);

        Order order3 = new Order();
        order3.setOrderId(3);
        order3.setDeliveryMethod(DeliveryMethod.POST);
        order3.setPaymentMethod(PaymentMethod.CREDIT_CARDS);

        Order order4 = new Order();
        order4.setOrderId(4);
        order4.setDeliveryMethod(null);
        order4.setPaymentMethod(null);

        Order order5 = new Order();
        order5.setOrderId(5);
        order5.setDeliveryMethod(null);
        order5.setPaymentMethod(null);

        List<Order> orders = new ArrayList<>();

        orders.add(order1);
        orders.add(order2);
        orders.add(order3);
        orders.add(order4);
        orders.add(order5);

        //expected
        List<Order> expected = new ArrayList<>();

        expected.add(order1);
        expected.add(order2);
        expected.add(order3);

        //mock
        when(orderDaoMock.getAllOrders()).thenReturn(orders);

        //actual
        List<Order> actual = orderService.getAllTrackedOrders();

        assertEquals(expected, actual);
    }

    @Test
    public void testGetAllTrackedOrdersByUserId_returnsListOfOrder(){

        //data
        User user1 = new User();
        user1.setId(1);

        User user2 = new User();
        user2.setId(2);

        Order order1 = new Order();
        order1.setOrderId(1);
        order1.setDeliveryMethod(DeliveryMethod.COURIER);
        order1.setPaymentMethod(PaymentMethod.APPLE_PAY);
        order1.setUser(user1);

        Order order2 = new Order();
        order2.setOrderId(2);
        order2.setDeliveryMethod(DeliveryMethod.PICKUP);
        order2.setPaymentMethod(PaymentMethod.MASTERPASS);
        order2.setUser(user2);

        Order order3 = new Order();
        order3.setOrderId(3);
        order3.setDeliveryMethod(DeliveryMethod.POST);
        order3.setPaymentMethod(PaymentMethod.CREDIT_CARDS);
        order3.setUser(user1);

        Order order4 = new Order();
        order4.setOrderId(4);
        order4.setDeliveryMethod(null);
        order4.setPaymentMethod(null);

        Order order5 = new Order();
        order5.setOrderId(5);
        order5.setDeliveryMethod(null);
        order5.setPaymentMethod(null);

        List<Order> orders = new ArrayList<>();

        orders.add(order1);
        orders.add(order2);
        orders.add(order3);
        orders.add(order4);
        orders.add(order5);

        //expected
        int userId = 2;
        List<Order> expected = new ArrayList<>();

        expected.add(order2);

        //mock
        when(orderDaoMock.getAllOrders()).thenReturn(orders);

        //actual
        List<Order> actual = orderService.getAllTrackedOrdersById(userId);

        assertEquals(expected, actual);
    }
}
