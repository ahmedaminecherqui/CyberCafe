package com.example.demo.entity;

import org.springframework.security.core.userdetails.UserDetails;

public interface AuthUser extends UserDetails {
    String getName();
    String getPhone();
    String getAddress();
}
