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
    public String loginPage(){
        return "login";
    }

    @PostMapping("/loginProcess")
    public String loginProcess(@ModelAttribute("username") String username, @ModelAttribute("password") String password, Model model, RedirectAttributes attributes){

            User loginUser = userService.validate(username, password);

            attributes.addFlashAttribute("username", username);
            attributes.addFlashAttribute("password", password);


            if(loginUser != null){
                return "redirect:/user/"+loginUser.getId();
            }

            else{
                model.addAttribute("STATUS_MESSAGE", "No user found");
                return "login";
            }
    }
}
