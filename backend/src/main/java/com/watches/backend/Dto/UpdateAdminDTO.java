package com.watches.backend.Dto;

public class UpdateAdminDTO {
    private String username;
    private String email;
    private String phone;

    public UpdateAdminDTO() {}

    public UpdateAdminDTO(String username, String email, String phone) {
        this.username = username;
        this.email = email;
        this.phone = phone;
    }

    // Getters
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }

    // Setters
    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
}
