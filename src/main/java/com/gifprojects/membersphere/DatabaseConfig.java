package com.gifprojects.membersphere;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConfig { 
    private static final String URL = "jdbc:mysql://localhost:3306/membersphere-management";
    private static final String USER = "root";
    private static final String PASSWORD = "358160";

    public static Connection getConnection(){
        try{
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e){
            System.out.println("Eroare: " + e.getMessage());
            return null;
        }
    }
}
