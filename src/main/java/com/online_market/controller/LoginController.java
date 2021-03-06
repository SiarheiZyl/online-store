package com.online_market.controller;

import com.online_market.entity.User;
import com.online_market.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Class for mapping all paths associated with login
 *
 * @author Siarhei
 * @version 1.0
 */
@Controller
@RequestMapping("/")
public class LoginController {

    /**
     * Apache log4j object is used to log all important info
     */
    final static Logger logger = Logger.getLogger(LoginController.class);

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
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Get mapping for logim page
     *
     * @param model model
     * @return login page
     */
    @GetMapping("/login")
    public String loginPage(Model model) {

        logger.info("Logging page");

        userService.logout();
        model.addAttribute("user", new User());

        return "login";
    }

    /**
     * Post mapping for login that verifies entered login and password
     *
     * @param user       user
     * @param model      model
     * @param attributes RedirectAttributes
     * @return redirect to the catalog if user entered correct data otherwise returning login page
     */
    @PostMapping("/loginProcess")
    public String loginProcess(@ModelAttribute("user") User user, Model model, RedirectAttributes attributes) {

        User loginUser = userService.validate(user.getLogin(), user.getPassword());

        attributes.addFlashAttribute("login", user.getLogin());
        attributes.addFlashAttribute("password", user.getPassword());


        if (loginUser != null) {
            userService.authorize(loginUser.getId());
            return "redirect:/catalog/1";
        } else {
            model.addAttribute("STATUS_MESSAGE", "Login or password is incorrect!");
            return "login";
        }
    }
}
