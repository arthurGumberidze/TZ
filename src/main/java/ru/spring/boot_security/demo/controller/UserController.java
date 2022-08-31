package ru.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.spring.boot_security.demo.model.Bank;
import ru.spring.boot_security.demo.model.User;
import ru.spring.boot_security.demo.service.BankService;
import ru.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/rest/api")
public class UserController {

    private final UserService userService;

    private final BankService bankService;

    @Autowired
    public UserController(UserService userService, BankService bankService) {
        this.userService = userService;
        this.bankService = bankService;
    }


    @GetMapping("/onlyUserss")
    public String getOnlyUser(Principal principal, Model model) {
        model.addAttribute("users", userService.index());
        model.addAttribute("userLogin", userService.getUserByUsername(principal.getName()));
        return principal.getName();
    }

    @GetMapping("/money")
    public String getOnlyUsers(Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        return String.valueOf(user.getBalance());
    }
    @GetMapping("/bankAccountOfEMoney")
    public Integer getEmoney(Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        return user.getEmoney();
    }


    @GetMapping("/payment/Shop/{amount}")
    public Integer paymentShop(Principal principal, @PathVariable int amount) {
        User user = userService.getUserByUsername(principal.getName());
        user.setBalance(user.getBalance()-amount);
        user.setEmoney((int) (user.getEmoney() + amount*0.1));
        userService.updateUser(user);
        return user.getBalance();
    }
    @GetMapping("/payment/Online/{amount}")
    public Integer paymentOnline(Principal principal, @PathVariable int amount) {
        User user = userService.getUserByUsername(principal.getName());
        user.setBalance(user.getBalance()-amount);
        if(amount>300){
            user.setEmoney((int) (user.getEmoney() + amount*0.3));
        }
        if(amount>20 && amount<300){
            user.setEmoney((int) (user.getEmoney() + amount*0.17));
        }
        if(amount < 20) {
            Bank bank = bankService.getBankById();
            bankService.updateBank(bank,amount);
        }
        userService.updateUser(user);
        return user.getBalance();
    }
}