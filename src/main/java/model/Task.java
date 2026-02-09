package model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Task implements Displayable {
    private long id;
    private String title;
    private String details;
    private String source; // Manager's email
    private String destination; // Employee's email
    private LocalDate deadline;
    private boolean completed;

    private Task(String title, String details, String source, String destination, String deadline) {
        this.title = title;
        this.details = details;
        if (!checkEmail(source)) throw new IllegalArgumentException("Invalid source email");
        this.source = source;
        if (!checkEmail(destination)) throw new IllegalArgumentException("Invalid destination email");
        this.destination = destination;
        setDeadline(deadline);
    }
    public static Task createTask(String title, String details, String source, String destination, String deadline){
        return new Task(title, details, source, destination, deadline);
    }

    public void setDeadline(String deadline) {
        try{
            this.deadline = LocalDate.parse(deadline);
        } catch (DateTimeParseException e){
            throw new IllegalArgumentException("Invalid date! Use YYYY-MM-DD format!");
        }
    }

    public void complete(){
        this.completed = true;
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

    public boolean checkEmail(String email){
        String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email != null && email.matches(emailPattern);
    }

    @Override
    public void showInfo(){
        System.out.println("The task: -" + getTitle() + "-, has been created.");
        System.out.println("Details: " + getDetails() + ",\nfrom: " + getSource() + ", to: " + getDestination());
        System.out.println("Deadline: " + getDeadline());
        System.out.print("Status: ");
        checkCompletion();
    }
}
