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

    private final List<Task> myTasks = new ArrayList<>();

    // this has to search the (to be implemented) database for that email
    public void receiveTask(Task task) {
        if (task.getDestination().equals(this.getEmail())) {
            myTasks.add(task);
        }
    }

    public void getTasks() {
        for (Task task : myTasks){
            task.showInfo();
        }
    }

    public void showInfo(){

    }
}
