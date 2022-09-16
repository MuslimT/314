package com.muslim.springboot.security.bootstrap.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.muslim.springboot.security.bootstrap.model.User;
import com.muslim.springboot.security.bootstrap.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    public String showUser(Principal principal, Model model){
        User user = userService.showUserByUsername(principal.getName());
        model.addAttribute("user", user);
        return "user";
    }


}
