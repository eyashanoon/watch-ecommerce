package com.watches.backend.Dto;

public class CreateAdminDTO {
    private String userName;
    private String email;
    private String password;
    private String phone;

    public CreateAdminDTO() {}

    public CreateAdminDTO(String username, String email, String password, String phone) {
        this.userName = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    // Getters
    public String getUsername() { return userName; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getPhone() { return phone; }

    // Setters
    public void setUsername(String username) { this.userName = username; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setPhone(String phone) { this.phone = phone; }
}
