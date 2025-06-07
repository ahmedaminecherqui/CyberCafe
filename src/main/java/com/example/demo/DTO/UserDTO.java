package com.example.demo.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserDTO {
    private Long id;

    @NotBlank(message = "{validation.name.required}")
    @Size(min = 2, max = 100, message = "{validation.name.size}")
    private String name;

    @NotBlank(message = "{validation.phone.required}")
    @Size(min = 10, max = 20, message = "{validation.phone.size}")
    private String phone;

    @NotBlank(message = "{validation.address.required}")
    @Size(min = 5, max = 255, message = "{validation.address.size}")
    private String address;

    @Min(value = 0, message = "{validation.wallet.min}")
    private double wallet;

    // Constructors
    public UserDTO() {}
    public UserDTO(Long id, String name, String phone, String address, double wallet) {
        this.id = id;
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
}
