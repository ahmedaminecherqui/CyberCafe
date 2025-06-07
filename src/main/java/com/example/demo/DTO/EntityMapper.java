package com.example.demo.DTO;


import com.example.demo.entity.Administrator;
import com.example.demo.entity.Product;
import com.example.demo.entity.User;
import com.example.demo.entity.UserProduct;
import org.springframework.stereotype.Component;

@Component
public class EntityMapper {

    // User mappings
    public UserDTO toUserDTO(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getPhone(), user.getAddress(), user.getWallet());
    }

    public User toUserEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setPhone(userDTO.getPhone());
        user.setAddress(userDTO.getAddress());
        user.setWallet(userDTO.getWallet());
        return user;
    }

    // Administrator mappings
    public AdministratorDTO toAdministratorDTO(Administrator admin) {
        return new AdministratorDTO(admin.getId(), admin.getName(), admin.getPhone(), admin.getAddress());
    }

    public Administrator toAdministratorEntity(AdministratorDTO adminDTO) {
        Administrator admin = new Administrator();
        admin.setId(adminDTO.getId());
        admin.setName(adminDTO.getName());
        admin.setPhone(adminDTO.getPhone());
        admin.setAddress(adminDTO.getAddress());
        return admin;
    }

    // Product mappings
    public ProductDTO toProductDTO(Product product) {
        return new ProductDTO(product.getId(), product.getName(), product.getType(), product.getPrice(), product.getStock());
    }

    public Product toProductEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setType(productDTO.getType());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());
        return product;
    }

    // UserProduct mappings
    public UserProductDTO toUserProductDTO(UserProduct userProduct) {
        return new UserProductDTO(
                userProduct.getId(),
                userProduct.getUser().getId(),
                userProduct.getProduct().getId(),
                userProduct.getProduct().getName(),
                userProduct.getProduct().getPrice(),
                userProduct.getQuantity()
        );
    }
}
