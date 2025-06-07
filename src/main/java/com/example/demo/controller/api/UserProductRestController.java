package com.example.demo.controller.api;

import com.example.demo.DTO.*;
import com.example.demo.entity.*;
import com.example.demo.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user-products")
public class UserProductRestController {

    @Autowired
    private UserProductService userProductService;

    @Autowired
    private UserService userService;

    @Autowired
    private EntityMapper entityMapper;

    @GetMapping
    public ResponseEntity<Page<UserProductDTO>> getAllUserProducts(Pageable pageable) {
        Page<UserProductDTO> userProductDTOPage = userProductService.getAllUserProducts(pageable)
                .map(entityMapper::toUserProductDTO);
        return ResponseEntity.ok(userProductDTOPage);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<UserProductDTO>> getUserProductsByUserId(@PathVariable Long userId, Pageable pageable) {
        Page<UserProductDTO> userProductDTOPage = userProductService.getUserProductsByUserId(userId, pageable)
                .map(entityMapper::toUserProductDTO);
        return ResponseEntity.ok(userProductDTOPage);
    }

    @PostMapping
    public ResponseEntity<UserProductDTO> buyProduct(@RequestBody UserProductDTO userProductDTO) {
        return userService.getUserById(userProductDTO.getUserId()).map(user -> {
            String result = userService.buyProduct(user, userProductDTO.getProductId(), userProductDTO.getQuantity());
            if (result.equals("success")) {
                return userProductService.getUserProductsByUserId(user.getId(), Pageable.unpaged())
                        .getContent().stream()
                        .filter(up -> up.getProduct().getId().equals(userProductDTO.getProductId()))
                        .findFirst()
                        .map(up -> ResponseEntity.status(HttpStatus.CREATED).body(entityMapper.toUserProductDTO(up)))
                        .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
            } else {
                throw new IllegalStateException(result);
            }
        }).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserProduct(@PathVariable Long id) {
        Optional<UserProduct> userProduct = userProductService.getUserProductById(id);
        if (userProduct.isPresent()) {
            userProductService.deleteUserProduct(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
