package com.watches.backend.model;
import jakarta.persistence.*;

@Entity
@Table(name = "admins")
public class Admin extends User{
    private final String  role = "ADMIN";

    public Admin(String userName,String email, String password, String phone ) {
        super(userName,email, password, phone);
    }
    public Admin() { }

}
