package com.gifprojects.membersphere;

import dao.TaskDAO;
import dao.UserDAO;
import model.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MembersphereApplication {
	public static void main(String[] args) {

        /* We will test getTasksByEmail now. */
		/*
		Employee a = Employee.createEmployee("John", "123", "john@gmail.com", "0723 223 432");
		UserDAO.saveUser(a);
		Manager b = Manager.createManager("Tom", "123", "tom@gmail.com", "0723 213 422");
		UserDAO.saveUser(b);

		TaskDAO.saveTask(Task.createTask("c", "d","tom@gmail.com","john@gmail.com","2003-11-10"));
		TaskDAO.saveTask(Task.createTask("h", "i","tom@gmail.com","john@gmail.com","2002-11-10"));
		*/

        TaskDAO.getTasksByEmail("john@gmail.com").forEach(System.out::println);

	}
}
