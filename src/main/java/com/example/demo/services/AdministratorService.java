package com.example.demo.services;

import com.example.demo.DAO.AdministratorRepository;
import com.example.demo.DAO.RoleRepository;
import com.example.demo.DAO.UserProductRepository;
import com.example.demo.entity.Administrator;
import com.example.demo.entity.Role;
import com.example.demo.entity.UserProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdministratorService {

    @Autowired
    private AdministratorRepository administratorRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserProductRepository userProductRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Administrator saveAdministrator(Administrator administrator) {
        return administratorRepository.save(administrator);
    }

    public Administrator signup(String name, String username, String password, String phone, String address) {
        Administrator admin = new Administrator();
        admin.setName(name);
        admin.setUsername(username);
        admin.setPassword(passwordEncoder.encode(password));
        admin.setPhone(phone);
        admin.setAddress(address);

        Role role = roleRepository.findByName("ROLE_ADMIN");
        if (role == null) {
            role = new Role("ROLE_ADMIN");
            roleRepository.save(role);
        }
        admin.setRole(role);

        return administratorRepository.save(admin);
    }

    public List<Administrator> getAllAdministrators() {
        return administratorRepository.findAll();
    }

    public Page<Administrator> getAllAdministrators(Pageable pageable) {
        return administratorRepository.findAll(pageable);
    }

    public Optional<Administrator> getAdministratorById(Long id) {
        return administratorRepository.findById(id);
    }

    public void deleteAdministrator(Long id) {
        administratorRepository.deleteById(id);
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