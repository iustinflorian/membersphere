package model;

public class Manager extends User implements Displayable {

    // this is a factory method
    private Manager(String username, String password, String email, String phone) {
        super(username, password, email, phone);
    }
    public static Manager createManager(String username, String password, String email, String phone) {
        return new Manager(username, password, email, phone);
    }
    // this is a factory method

    public Task createTask(String title, String details, String employeeEmail, String deadline, boolean completed) {
        return new Task(title, details, this.getEmail(), employeeEmail, deadline, completed);
    }

    public void showInfo(){

    }
}
