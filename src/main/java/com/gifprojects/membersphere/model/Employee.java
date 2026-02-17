package com.gifprojects.membersphere.model;

public class Employee extends User {

    private Employee(String username, String password, String email, String phone) {
        super(username, password, email, phone);
    }
    public static Employee createEmployee(String username, String password, String email, String phone) {
        return new Employee(username, password, email, phone);
    }
}