package com.watches.backend.Dto;

public class AdminDTO {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private final String role = "ADMIN";

    public AdminDTO() {}

    public AdminDTO(Long id, String username, String email, String phone) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phone = phone;
    }

    // Getters
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getRole() { return role; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }

    // No setter for role (fixed)
}
