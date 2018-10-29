package com.online_market.controller;

import com.online_market.entity.Address;
import com.online_market.entity.User;
import com.online_market.service.AddressService;
import com.online_market.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
public class UserController {

    @Autowired
    public UserService userService;

    @Autowired
    public AddressService addressService;

    @GetMapping("/")
    public String index() {
        return "in";
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

    @GetMapping("/userList")
    public String listCustomers(Model model) {
        model.addAttribute("user", new User());
        List<User> list = this.userService.findAll();
        model.addAttribute("userList", list);
        return "userList";
    }

    @GetMapping("/user/{id}/address")
    public String getAddress(@PathVariable("id") int id, Model model){
        Address address = userService.getById(id).getAddress() == null ? new Address() : userService.getById(id).getAddress();

        model.addAttribute("address", address);
        model.addAttribute("id", id);

        return "address";
    }

    @PostMapping("user/{id}/addressProcess")
    public String updateAddress(@PathVariable("id") int id, @ModelAttribute("address") Address address) {
        addressService.update(address);

        User user = userService.getById(id);
        user.setAddress(address);

        userService.update(user);

        return "redirect:/user/" + user.getId();
    }

}
