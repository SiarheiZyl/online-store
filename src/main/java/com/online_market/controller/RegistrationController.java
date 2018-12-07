package com.online_market.controller;


import com.online_market.entity.Item;
import com.online_market.entity.User;
import com.online_market.entity.enums.Roles;
import com.online_market.service.OrderService;
import com.online_market.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Map;

/**
 * Class for mapping all paths associated with registration
 *
 * @author Siarhei
 * @version 1.0
 */
@Controller
public class RegistrationController {

    /**
     * Apache log4j object is used to log all important info
     */
    final static Logger logger = Logger.getLogger(RegistrationController.class);

    /**
     * User service object. See {@link com.online_market.service.UserServiceImpl}
     */
    private final UserService userService;

    /**
     * Injecting constructor
     *
     * @param userService user service
     */
    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Get mapping for register page
     *
     * @param model model
     * @return registration page
     */
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String showRegister(Model model) {

        model.addAttribute("user", new User());

        return "register";
    }


    /**
     * Post mapping for registration new user
     *
     * @param user new user
     * @return redirect to user page
     */
    @PostMapping("/registerProcess")
    public String addUser(@ModelAttribute("user") User user, Model model) {

        if (!userService.isLoginUnique(user)) {
            model.addAttribute("LOGIN_MESSAGE", "This login is already taken by another user.");
            return "register";
        }

        logger.info("Registration process started.");

        userService.register(user);

        return "redirect:/user/" + user.getId();
    }
}
