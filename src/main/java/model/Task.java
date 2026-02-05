package model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Task implements Displayable {
    private String weight; // task's weight
    private String source; // manager's email
    private String destination; // employee's email
    private String title;
    private String details;
    private boolean completed;
    private LocalDate deadline;

    public Task(String title, String details, String source, String destination, String deadline, boolean completed) {
        this.title = title;
        this.details = details;
        this.source = source;
        setEmail(destination);
        this.completed = completed;
        setDeadline(deadline);
    }

    public void setDeadline(String deadline) {
        try{
            this.deadline = LocalDate.parse(deadline);
        } catch (DateTimeParseException e){
            throw new IllegalArgumentException("Invalid date! Use YYYY-MM-DD format!");
        }
    }

    public void checkCompletion(){
        if (this.completed) {
            System.out.println("The task has been completed!");
        } else {
            LocalDate today = LocalDate.now();
            if (this.deadline.isBefore(today)){
                long daysOverdue = ChronoUnit.DAYS.between(this.deadline, today);
                System.out.println("Your task is OVERDUE by " + daysOverdue + "days!");
            }else{
                long daysLeft = ChronoUnit.DAYS.between(today, this.deadline);
                System.out.println("The task hasn't been completed! Remaining days: " + daysLeft);
            }
        }
    }

    // checking if email is similar to the standard pattern
    public final void setEmail(String email) {
        if(checkEmail(email)){
            this.destination = email;
        } else {
            throw new IllegalArgumentException("Invalid email address");
        }
    }

    public boolean checkEmail(String email){
        String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email != null && email.matches(emailPattern);
    }

    @Override
    public void showInfo(){
        System.out.println("The task: -" + getTitle() + "-, has been created.");
        System.out.println("Details: " + getDetails() + ",\nfrom: " + getSource() + ", to: " + getDestination());
        System.out.println("Deadline: " + getDeadline());
        System.out.println("Importance: " + getWeight());
        System.out.print("Status: ");
        checkCompletion();
    }
}
