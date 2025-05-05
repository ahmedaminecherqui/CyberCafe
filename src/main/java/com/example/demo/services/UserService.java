package com.example.demo.services;

import com.example.demo.DAO.*;
import com.example.demo.entite.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserProductRepository userProductRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public String buyProduct(User user, Long productId, int quantity) {
        Optional<User> optionalUser = userRepository.findById(user.getId());
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if (!optionalUser.isPresent()) {
            return "User not found.";
        }
        if (!optionalProduct.isPresent()) {
            return "Product not found.";
        }

        User u = optionalUser.get();
        Product p = optionalProduct.get();

        // Calculate total cost
        double totalCost = p.getPrice() * quantity;

        // Check wallet balance
        if (u.getWallet() < totalCost) {
            return "Insufficient funds. Required: " + totalCost + ", Available: " + u.getWallet();
        }

        // Check stock availability
        if (p.getStock() < quantity) {
            return "Insufficient stock. Requested: " + quantity + ", Available: " + p.getStock();
        }

        // Deduct from wallet and stock
        u.setWallet(u.getWallet() - totalCost);
        p.setStock(p.getStock() - quantity);

        // Save updated user and product
        userRepository.save(u);
        productRepository.save(p);

        // Create or update UserProduct entry
        UserProduct userProduct = new UserProduct(u, p, quantity);
        userProductRepository.save(userProduct);

        return "success";
    }
}