package com.gifprojects.membersphere;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import dao.UserDAO;
import model.Employee;
import model.Manager;
import model.Task;

import model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MembersphereApplication {
	public static void main(String[] args) {

		/* TEST BAZA DE DATE
		Connection conn = DatabaseConfig.getConnection();
		if (conn != null) {
			System.out.println("Connected to database.");
		} else {
			System.err.println("Connection failed!");
		}
		*/

		Employee testEmployee = Employee.createEmployee("AlexNiculae2", "masina123", "alexniculae223@gmail.com", "0765 812 334");
		Manager testManager = Manager.createManager("GaitaIustin3", "masina1234", "iustingaita2@gmail.com", "0743 231 222");
		//UserDAO.saveUser(testEmployee);
		//UserDAO.saveUser(testManager);
		UserDAO.removeUser(testEmployee);
	}
}
