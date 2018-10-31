package com.online_market.controller;

import com.online_market.entity.User;
import com.online_market.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/")
public class LoginController {

    @Autowired
    public UserService userService;

    @GetMapping("/login")
    public String loginPage(Model model){

        userService.logout();
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/loginProcess")
    public String loginProcess(@ModelAttribute("user") User user, Model model, RedirectAttributes attributes){

            User loginUser = userService.validate(user.getLogin(), user.getPassword());

            attributes.addFlashAttribute("login", user.getLogin());
            attributes.addFlashAttribute("password", user.getPassword());


            if(loginUser != null){
                userService.authorize(loginUser.getId());
                return "redirect:/user/"+loginUser.getId();
            }

            else{
                model.addAttribute("STATUS_MESSAGE", "No user found");
                return "login";
            }
    }
}
