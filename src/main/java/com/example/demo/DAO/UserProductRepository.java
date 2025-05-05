package com.example.demo.DAO;


import com.example.demo.entite.UserProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserProductRepository extends JpaRepository<UserProduct, Long> {
    List<UserProduct> findByUserId(Long userId);
}
