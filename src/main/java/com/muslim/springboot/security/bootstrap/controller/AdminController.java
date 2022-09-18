package com.muslim.springboot.security.bootstrap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.muslim.springboot.security.bootstrap.model.User;
import com.muslim.springboot.security.bootstrap.service.RoleService;
import com.muslim.springboot.security.bootstrap.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;


    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String getUsers(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.showUserByUsername(userDetails.getUsername());
        model.addAttribute("users", userService.index());
        model.addAttribute("user", user);
        model.addAttribute("newUser", new User());
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin";
    }


    @PostMapping("/new")
    public String createUser(@ModelAttribute("user") User user, @RequestParam (value = "nameRoles", required = false) String roles)  {
        user.setRoles(roleService.getByName(roles));
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") long id){
        model.addAttribute("user", userService.showUser(id));
        return "edit";
    }

    @PatchMapping("/edit/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") long id, @RequestParam (value = "nameRoles", required = false) String roles) {
        user.setRoles(roleService.getByName(roles));
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
