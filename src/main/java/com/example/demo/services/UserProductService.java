package com.example.demo.services;

import com.example.demo.DAO.*;
import com.example.demo.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<UserProduct> getAllUserProducts(Pageable pageable) {
        return userProductRepository.findAll(pageable);
    }

    public Optional<UserProduct> getUserProductById(Long id) {
        return userProductRepository.findById(id);
    }

    public void deleteUserProduct(Long id) {
        userProductRepository.deleteById(id);
    }

    public Page<UserProduct> getUserProductsByUserId(Long userId, Pageable pageable) {
        return userProductRepository.findByUserId(userId, pageable);
    }

    public List<UserProduct> getUserProductsByUserId(Long userId) {
        return userProductRepository.findByUserId(userId, Pageable.unpaged()).getContent();
    }
}