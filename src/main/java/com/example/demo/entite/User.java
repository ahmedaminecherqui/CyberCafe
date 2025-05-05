package com.example.demo.entite;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private double wallet;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserProduct> purchasedProducts;

    // Constructors
    public User() {}
    public User(String name, String phone, String address, double wallet) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.wallet = wallet;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public double getWallet() { return wallet; }
    public void setWallet(double wallet) { this.wallet = wallet; }
    public List<UserProduct> getPurchasedProducts() { return purchasedProducts; }
    public void setPurchasedProducts(List<UserProduct> purchasedProducts) { this.purchasedProducts = purchasedProducts; }
}
