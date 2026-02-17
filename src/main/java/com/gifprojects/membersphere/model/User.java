package com.gifprojects.membersphere.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class User {
    private String username;
    private String email;
    private String phone;
    private long id;
    private String password;
    private String role;

    public User(String username, String password, String email, String phone, String role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.role = role;
    }
}
