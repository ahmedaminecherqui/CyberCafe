package com.example.demo.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ProductDTO {
    private Long id;

    @NotBlank(message = "{validation.product.name.required}")
    @Size(min = 2, max = 100, message = "{validation.product.name.size}")
    private String name;

    @NotBlank(message = "{validation.product.type.required}")
    @Size(min = 2, max = 50, message = "{validation.product.type.size}")
    private String type;

    @Min(value = 0, message = "{validation.product.price.min}")
    private double price;

    @Min(value = 0, message = "{validation.product.stock.min}")
    private int stock;

    // Constructors
    public ProductDTO() {}
    public ProductDTO(Long id, String name, String type, double price, int stock) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
        this.stock = stock;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}
