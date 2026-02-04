package com.gifprojects.membersphere;

import model.Employee;
import model.Manager;
import model.Task;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MembersphereApplication {
	public static void main(String[] args) {

		Employee emp1 = new Employee("tomas223", "letsgo", "tomas@gmail.com",735324545);
		Employee emp2 = new Employee("arnold2", "mods", "arnold@gmail.com",754623212);

		Manager man = new Manager("admin1", "yes", "manage@gmail.com",76223212);

		Task newTask = man.create_task("First task", "This is your first task", "tomas@gmail.com",  "Tommorow");

		emp1.receive_task(newTask);

		emp1.get_tasks();

	}
}
