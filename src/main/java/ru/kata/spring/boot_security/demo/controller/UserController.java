package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public String getUser(Principal principal, Model model) {
        model.addAttribute("users", userService.index());
        model.addAttribute("userLogin", userService.getUserByUsername(principal.getName()));
        return "user";
    }

    @GetMapping("/onlyUserss")
    public String getOnlyUser(Principal principal, Model model) {
        model.addAttribute("users", userService.index());
        model.addAttribute("userLogin", userService.getUserByUsername(principal.getName()));
        return "onlyusers";
    }
}