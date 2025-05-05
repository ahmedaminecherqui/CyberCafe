package com.example.demo.services;

import com.example.demo.DAO.*;
import com.example.demo.entite.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdministratorService {
    @Autowired
    private AdministratorRepository administratorRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserProductRepository userProductRepository;

    public Administrator saveAdministrator(Administrator administrator) {
        return administratorRepository.save(administrator);
    }

    public List<Administrator> getAllAdministrators() {
        return administratorRepository.findAll();
    }

    public Optional<Administrator> getAdministratorById(Long id) {
        return administratorRepository.findById(id);
    }

    public void deleteAdministrator(Long id) {
        administratorRepository.deleteById(id);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public double getTotalPayments() {
        List<UserProduct> userProducts = userProductRepository.findAll();
        double total = 0.0;
        for (UserProduct up : userProducts) {
            total += up.getQuantity() * up.getProduct().getPrice();
        }
        return total;
    }
}