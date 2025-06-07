package com.example.demo.controller;

import com.example.demo.entity.*;
import com.example.demo.services.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserProductService userProductService;
    @Autowired
    private ProductService productService;

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new User());
        return "users/create";
    }

    @PostMapping
    public String createUser(@ModelAttribute("user") User user, Model model) {
        userService.saveUser(user);
        return "redirect:/users";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        return userService.getUserById(id).map(user -> {
            model.addAttribute("user", user);
            return "users/edit";
        }).orElse("redirect:/users");
    }

    @PostMapping("/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute("user") User user, Model model) {
        user.setId(id);
        userService.saveUser(user);
        return "redirect:/users";
    }

    @GetMapping("/{id}/delete")
    public String showDeleteConfirmation(@PathVariable Long id, Model model) {
        return userService.getUserById(id).map(user -> {
            model.addAttribute("user", user);
            return "users/delete";
        }).orElse("redirect:/users");
    }

    @PostMapping("/{id}/delete")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }

    @GetMapping("/{id}/products")
    public String listUserProducts(@PathVariable Long id, Model model) {
        return userService.getUserById(id).map(user -> {
            model.addAttribute("user", user);
            model.addAttribute("purchasedProducts", userProductService.getUserProductsByUserId(id));
            return "users/products";
        }).orElse("redirect:/users");
    }

    @GetMapping("/{id}/products/new")
    public String showBuyProductForm(@PathVariable Long id, Model model) {
        model.addAttribute("userId", id);
        model.addAttribute("products", productService.getAllProducts());
        return "users/buy";
    }

    @PostMapping("/{id}/products")
    public String buyProduct(@PathVariable Long id, @RequestParam Long productId, @RequestParam int quantity, Model model, RedirectAttributes redirectAttributes) {
        return userService.getUserById(id).map(user -> {
            String result = userService.buyProduct(user, productId, quantity);
            if (result.equals("success")) {
                return "redirect:/users/" + id + "/products";
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", result);
                return "redirect:/users/" + id + "/products/new";
            }
        }).orElse("redirect:/users");
    }
}
