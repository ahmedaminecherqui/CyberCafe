package com.example.demo.DAO;




import com.example.demo.entity.UserProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProductRepository extends JpaRepository<UserProduct, Long> {
    Page<UserProduct> findByUserId(Long userId, Pageable pageable);
}
