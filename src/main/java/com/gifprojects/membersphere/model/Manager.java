package com.gifprojects.membersphere.model;

public class Manager extends User {

    // this is a factory method
    private Manager(String username, String password, String email, String phone) {
        super(username, password, email, phone, "Manager");
    }
    public static Manager createManager(String username, String password, String email, String phone) {
        return new Manager(username, password, email, phone);
    }
    // this is a factory method
}
