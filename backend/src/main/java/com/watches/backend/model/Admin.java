package com.watches.backend.model;

import com.watches.backend.enums.Role;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
 
public class Admin extends User {

    public Admin() {
        super();
    }

    public Admin(String userName, String email, String password, String phone) {
        super(userName, email, password, phone);
        this.setRole(Role.ADMIN);
    }




    // Optional: you could add admin-specific logic here
}



