CREATE DATABASE IF NOT EXISTS cybercafe_db;
USE cybercafe_db;

-- Create the roles table
CREATE TABLE roles (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       name VARCHAR(50) NOT NULL UNIQUE
);

-- Update the users table to include authentication fields
CREATE TABLE users (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       name VARCHAR(255) NOT NULL,
                       username VARCHAR(50) NOT NULL UNIQUE, -- Added for authentication
                       password VARCHAR(255) NOT NULL,       -- Added for authentication (hashed)
                       phone VARCHAR(20) NOT NULL,
                       address VARCHAR(255) NOT NULL,
                       wallet DOUBLE NOT NULL,
                       role_id BIGINT NOT NULL,              -- Added for role-based authorization
                       FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE RESTRICT
);

-- Administrators table (optional, if you want to keep it separate)
CREATE TABLE administrators (
                                id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                name VARCHAR(255) NOT NULL,
                                username VARCHAR(50) NOT NULL UNIQUE, -- Added for authentication
                                password VARCHAR(255) NOT NULL,       -- Added for authentication (hashed)
                                phone VARCHAR(20) NOT NULL,
                                address VARCHAR(255) NOT NULL,
                                role_id BIGINT NOT NULL,              -- Added for role-based authorization
                                FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE RESTRICT
);

CREATE TABLE products (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          name VARCHAR(255) NOT NULL,
                          type VARCHAR(100) NOT NULL,
                          price DOUBLE NOT NULL,
                          stock INT NOT NULL
);

CREATE TABLE user_products (
                               id BIGINT PRIMARY KEY AUTO_INCREMENT,
                               user_id BIGINT NOT NULL,
                               product_id BIGINT NOT NULL,
                               quantity INT NOT NULL,
                               FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                               FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

-- Insert initial roles
INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');