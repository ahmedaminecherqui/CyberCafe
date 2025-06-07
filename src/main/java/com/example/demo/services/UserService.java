package com.example.demo.services;

import com.example.demo.DAO.ProductRepository;
import com.example.demo.DAO.RoleRepository;
import com.example.demo.DAO.UserProductRepository;
import com.example.demo.DAO.UserRepository;
import com.example.demo.entity.Product;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.entity.UserProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User signup(String name, String username, String password, String phone, String address, double wallet, String roleName) {
        User user = new User();
        user.setName(name);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setPhone(phone);
        user.setAddress(address);
        user.setWallet(wallet);

        Role role = roleRepository.findByName(roleName);
        if (role == null) {
            role = new Role(roleName);
            roleRepository.save(role);
        }
        user.setRole(role);

        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
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

        double totalCost = p.getPrice() * quantity;

        if (u.getWallet() < totalCost) {
            return "Insufficient funds. Required: " + totalCost + ", Available: " + u.getWallet();
        }

        if (p.getStock() < quantity) {
            return "Insufficient stock. Requested: " + quantity + ", Available: " + p.getStock();
        }

        u.setWallet(u.getWallet() - totalCost);
        p.setStock(p.getStock() - quantity);

        userRepository.save(u);
        productRepository.save(p);

        UserProduct userProduct = new UserProduct(u, p, quantity);
        userProductRepository.save(userProduct);

        return "success";
    }
}