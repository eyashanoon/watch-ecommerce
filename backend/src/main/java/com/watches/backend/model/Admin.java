package com.watches.backend.model;

import jakarta.persistence.Entity;

import java.util.HashSet;

@Entity
public class Admin extends User {

    public Admin() {
        this.setRoles(new HashSet<>());
    }

    public Admin(String userName, String email, String password, String phone) {
        super(userName, email, password, phone);
        this.setRoles(new HashSet<>());
    }
}



