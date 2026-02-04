package model;

import java.util.ArrayList;
import java.util.List;

public class Employee extends User {

    public Employee(String username, String password, String email, double phone) {
        set_username(username);
        set_password(password);
        set_email(email);
        set_phone(phone);
    }

    private List<Task> myTasks = new ArrayList<>();

    public void receive_task(Task task) {
        if (task.get_to().equals(this.get_email())) {
            myTasks.add(task);
        }
    }

    public void get_tasks() {
        for (Task task : myTasks){
            task.get_info();
        }
    }
}
