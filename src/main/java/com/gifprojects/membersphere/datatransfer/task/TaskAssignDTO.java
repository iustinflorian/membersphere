package com.gifprojects.membersphere.datatransfer.task;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TaskAssignDTO {
    long id;
    String title;
    String details;
    String source; // Manager's email
    String destination; // Employee's email
    LocalDate deadline;
    boolean completed;
}
