package com.watches.backend;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordValidationTest {
    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // Example raw password and hashed password from DB
        String rawPassword = "123456/*7Aa";
        String hashedPassword = "$2a$10$EuV8aOPrzMyo20z9Aj6/Ye2NuRXhRTOFSkS2H2du9hQlZcg.ULFSK";  // From DB

        boolean isMatch = passwordEncoder.matches(rawPassword, hashedPassword);

        System.out.println("Password Match? " + isMatch);
    }
}
