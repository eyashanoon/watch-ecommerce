package com.watches.backend.model;

import jakarta.persistence.*;
import com.watches.backend.enums.*;

import java.util.ArrayList;
import java.util.List;

@Entity
 
@Inheritance(strategy = InheritanceType.JOINED)
 public   class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;
    private String email;


    private String password;
    private String phone;
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<Role> roles = new ArrayList<>();





    public User() {
    }

    public User(String userName, String email, String password, String phone) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // Abstract method that must be implemented by subclasses like Customer or Admin
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Role> getRoles() {
        return roles;
    }
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

}
