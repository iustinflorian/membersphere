package model;

public class Manager extends User {

    public Manager(String username, String password, String email, double phone) {
        set_username(username);
        set_password(password);
        set_email(email);
        set_phone(phone);
    }

    public Task create_task(String title, String details, String employeeEmail, String deadline) {
        return new Task(title, details, this.get_email(), employeeEmail, deadline);
    }

}
