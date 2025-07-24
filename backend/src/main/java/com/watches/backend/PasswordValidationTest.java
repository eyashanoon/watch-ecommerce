package com.watches.backend;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordValidationTest {
    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // Example raw password and hashed password from DB
        String rawPassword = "0000";
        String hashedPassword = "$2a$10$VjI/m.dbWKpj39nX/.YBhu1NVaDwfBuvqyJbHT31S6BUiCrb7AjTq";  // From DB

        boolean isMatch = passwordEncoder.matches(rawPassword, hashedPassword);

        System.out.println("Password Match? " + isMatch);
    }
}
