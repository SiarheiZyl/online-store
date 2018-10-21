package com.online_market.controller;


import com.online_market.entity.User;
import com.online_market.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class RegistrationController {

    @Autowired
    public UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String showRegister(Model model) {

        model.addAttribute("user", new User());
        model.addAttribute("Placeholder", "0-Client, 1-Admin");
        return "register";
    }


    @PostMapping("/registerProcess")
    public String addUser( @ModelAttribute("user") User user) {

        userService.register(user);
        return "redirect:/user/"+user.getId();
    }
}
