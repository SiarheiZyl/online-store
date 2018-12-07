package com.online_market.controller;

import com.online_market.entity.Address;
import com.online_market.entity.User;
import com.online_market.service.AddressService;
import com.online_market.service.ItemService;
import com.online_market.service.OrderService;
import com.online_market.service.UserService;
import com.online_market.utils.HashPasswordUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Class for mapping all paths associated with{@link User} information
 *
 * @author Siarhei
 * @version 1.0
 */
@Controller
@RequestMapping("/")
public class UserController {

    /**
     * Apache log4j object is used to log all important info
     */
    final static Logger logger = Logger.getLogger(UserController.class);

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
     * Address service object. See {@link com.online_market.service.AddressServiceImpl}
     */
    private final AddressService addressService;

    /**
     * Injecting constructor
     *
     * @param userService    user service
     * @param addressService address service
     * @param itemService    item service
     * @param orderService   order service
     */
    @Autowired
    public UserController(UserService userService, AddressService addressService, ItemService itemService, OrderService orderService) {
        this.userService = userService;
        this.addressService = addressService;
        this.itemService = itemService;
        this.orderService = orderService;
    }

    @GetMapping("/")
    public String index() {

        userService.logout();
        return "in";
    }

    /**
     * Get mapping for user profile
     *
     * @param id    user id
     * @param model model
     * @return page with user info if user authorized
     */
    @GetMapping("/user/{id}")
    public String getById(@PathVariable("id") int id, Model model) {

        if (userService.getById(id).isAuth()) {
            model.addAttribute("role", userService.getById(id).getRole());
            model.addAttribute("user", userService.getById(id));
            model.addAttribute("placeHolderForPassword", "New password");
            model.addAttribute("id", id);
            model.addAttribute("numberOfItemsInBucket", itemService.getOrderSize(itemService.getOrderNotNullItems(orderService.getBucketOrder(id).getOrderId())));

            logger.info("User with id: " + id + "visited userInfo page.");

            return "userInfo";
        } else {
            return "redirect:/";
        }
    }

    /**
     * Post mapping for updating user info
     *
     * @param user user
     * @return user
     */
    @PostMapping("/updateUser")
    @ResponseBody
    public User updateUser(@ModelAttribute("user") User user) {

        user.setAuth(true);
        userService.setPasswordHash(user);
        userService.update(user);

        return user;
    }

    /**
     * Get mapping for address page
     *
     * @param id    user id
     * @param model model
     * @return address page if user autorized
     */
    @GetMapping("/user/{id}/address")
    public String getAddress(@PathVariable("id") int id, Model model) {

        if (userService.getById(id).isAuth()) {
            Address address = userService.getById(id).getAddress() == null ? new Address() : userService.getById(id).getAddress();

            model.addAttribute("role", userService.getById(id).getRole());
            model.addAttribute("address", address);
            model.addAttribute("id", id);

            model.addAttribute("numberOfItemsInBucket", itemService.getOrderSize(itemService.getOrderNotNullItems(orderService.getBucketOrder(id).getOrderId())));

            logger.info("User with id: " + id + "visited address page.");

            return "address";
        } else {
            return "redirect:/";
        }
    }

    /**
     * Post mapping for updating address
     *
     * @param id      id
     * @param address address
     * @return address
     */
    @PostMapping("user/{id}/addressProcess")
    @ResponseBody
    public Address updateAddress(@PathVariable("id") int id, @ModelAttribute("address") Address address) {

        addressService.update(address);

        User user = userService.getById(id);
        user.setAddress(address);

        userService.update(user);

        return address;
    }
}
