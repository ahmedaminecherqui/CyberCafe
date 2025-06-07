package com.example.demo.controller;

import com.example.demo.entity.Administrator;
import com.example.demo.entity.Product;
import com.example.demo.services.AdministratorService;
import com.example.demo.services.ProductService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/administrators")
public class AdministratorController {

    @Autowired
    private AdministratorService administratorService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @GetMapping
    public String listAdministrators(Model model) {
        model.addAttribute("administrators", administratorService.getAllAdministrators());
        return "administrators/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("administrator", new Administrator());
        return "administrators/create";
    }

    @PostMapping
    public String createAdministrator(@Valid @ModelAttribute("administrator") Administrator administrator, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "administrators/create";
        }
        administratorService.saveAdministrator(administrator);
        return "redirect:/administrators";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        return administratorService.getAdministratorById(id).map(admin -> {
            model.addAttribute("administrator", admin);
            return "administrators/edit";
        }).orElse("redirect:/administrators");
    }

    @PostMapping("/{id}")
    public String updateAdministrator(@PathVariable Long id, @Valid @ModelAttribute("administrator") Administrator administrator, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "administrators/edit";
        }
        administrator.setId(id);
        administratorService.saveAdministrator(administrator);
        return "redirect:/administrators";
    }

    @GetMapping("/{id}/delete")
    public String showDeleteConfirmation(@PathVariable Long id, Model model) {
        return administratorService.getAdministratorById(id).map(admin -> {
            model.addAttribute("administrator", admin);
            return "administrators/delete";
        }).orElse("redirect:/administrators");
    }

    @PostMapping("/{id}/delete")
    public String deleteAdministrator(@PathVariable Long id) {
        administratorService.deleteAdministrator(id);
        return "redirect:/administrators";
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("totalPayments", administratorService.getTotalPayments());
        return "administrators/users";
    }

    @GetMapping("/products")
    public String listProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "administrators/products";
    }

    @GetMapping("/products/new")
    public String showCreateProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "administrators/create_product";
    }

    @PostMapping("/products")
    public String createProduct(@Valid @ModelAttribute("product") Product product, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "administrators/create_product";
        }
        productService.saveProduct(product);
        return "redirect:/administrators/products";
    }

    @GetMapping("/products/{id}/edit")
    public String showEditProductForm(@PathVariable Long id, Model model) {
        return productService.getProductById(id).map(product -> {
            model.addAttribute("product", product);
            return "administrators/edit_product";
        }).orElse("redirect:/administrators/products");
    }

    @PostMapping("/products/{id}")
    public String updateProduct(@PathVariable Long id, @Valid @ModelAttribute("product") Product product, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "administrators/edit_product";
        }
        product.setId(id);
        productService.saveProduct(product);
        return "redirect:/administrators/products";
    }

    @GetMapping("/products/{id}/delete")
    public String showDeleteProductConfirmation(@PathVariable Long id, Model model) {
        return productService.getProductById(id).map(product -> {
            model.addAttribute("product", product);
            return "administrators/delete_product";
        }).orElse("redirect:/administrators/products");
    }

    @PostMapping("/products/{id}/delete")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/administrators/products";
    }
}
