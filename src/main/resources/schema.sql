CREATE DATABASE IF NOT EXISTS cybercafe_db;
USE cybercafe_db;

CREATE TABLE users (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       name VARCHAR(255) NOT NULL,
                       phone VARCHAR(20) NOT NULL,
                       address VARCHAR(255) NOT NULL,
                       wallet DOUBLE NOT NULL
);

CREATE TABLE administrators (
                                id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                name VARCHAR(255) NOT NULL,
                                phone VARCHAR(20) NOT NULL,
                                address VARCHAR(255) NOT NULL
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