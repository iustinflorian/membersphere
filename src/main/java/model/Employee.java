package model;

import java.util.ArrayList;
import java.util.List;

public class Employee extends User implements Displayable {

    private Employee(String username, String password, String email, String phone) {
        super(username, password, email, phone);
    }
    public static Employee createEmployee(String username, String password, String email, String phone) {
        return new Employee(username, password, email, phone);
    }

    @Override
    public void showInfo(){
        System.out.println("Employee email: " + this.getEmail());
    }
}