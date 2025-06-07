package com.example.demo.controller;

import com.example.demo.services.AdministratorService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AdministratorService administratorService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/signup")
    public String showSignupForm() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(
            @RequestParam String name,
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String phone,
            @RequestParam String address,
            @RequestParam(defaultValue = "0.0") double wallet,
            @RequestParam(defaultValue = "user") String userType) {
        if ("admin".equalsIgnoreCase(userType)) {
            administratorService.signup(name, username, password, phone, address);
        } else {
            userService.signup(name, username, password, phone, address, wallet, "ROLE_USER");
        }
        return "redirect:/login";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }
}
