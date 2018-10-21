package com.online_market.controller;

import com.online_market.entity.User;
import com.online_market.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class UserController {

    @Autowired
    public UserService userService;

    @GetMapping("/")
    public String index() {
        return "in";
    }

    @GetMapping("/users")
    public String getAllUsers(Model model) {

        model.addAttribute("users", userService.findAll());

        return "usersList";
    }

    @GetMapping("/user/{id}")
    public String getById(@PathVariable("id") int id, Model model) {

        model.addAttribute("user", userService.getById(id));
        model.addAttribute("placeHolderForPassword", "New password");

        return "userInfo";
    }

    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.update(user);
        return "redirect:/user/" + user.getId();
    }

}
