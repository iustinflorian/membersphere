package com.gifprojects.membersphere.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Task {
    private long id;
    private String title;
    private String details;
    private String source; // Manager's email
    private String destination; // Employee's email
    private Long sourceId;
    private Long destinationId;
    private LocalDate deadline;
    private boolean completed;

    private Task(String title, String details, String source, String destination, String deadline) {
        this.title = title;
        this.details = details;
        this.source = source;
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

/*
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
*/
}
