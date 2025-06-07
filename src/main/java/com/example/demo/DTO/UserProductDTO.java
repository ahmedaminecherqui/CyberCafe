package com.example.demo.DTO;

import jakarta.validation.constraints.Min;

public class UserProductDTO {
    private Long id;
    private Long userId;
    private Long productId;
    private String productName;
    private double productPrice;

    @Min(value = 1, message = "{validation.userproduct.quantity.min}")
    private int quantity;

    // Constructors
    public UserProductDTO() {}
    public UserProductDTO(Long id, Long userId, Long productId, String productName, double productPrice, int quantity) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = quantity;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public double getProductPrice() { return productPrice; }
    public void setProductPrice(double productPrice) { this.productPrice = productPrice; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
