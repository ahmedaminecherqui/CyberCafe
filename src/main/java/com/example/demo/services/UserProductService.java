package com.example.demo.services;

import com.example.demo.DAO.*;
import com.example.demo.entite.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserProductService {
    @Autowired
    private UserProductRepository userProductRepository;

    public UserProduct saveUserProduct(UserProduct userProduct) {
        return userProductRepository.save(userProduct);
    }

    public List<UserProduct> getAllUserProducts() {
        return userProductRepository.findAll();
    }

    public Optional<UserProduct> getUserProductById(Long id) {
        return userProductRepository.findById(id);
    }

    public void deleteUserProduct(Long id) {
        userProductRepository.deleteById(id);
    }

    public List<UserProduct> getUserProductsByUserId(Long userId) {
        return userProductRepository.findByUserId(userId);
    }
}