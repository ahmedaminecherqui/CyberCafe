package com.example.demo.DAO;

import com.example.demo.entite.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}