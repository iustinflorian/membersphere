package dao;

import com.gifprojects.membersphere.DatabaseConfig;
import model.Manager;
import model.Task;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskDAO {

    public static void saveTask(Task task) {
        String sql = "INSERT INTO tasks (title, details, source, destination, deadline, completed) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDetails());
            stmt.setString(3, task.getSource());
            stmt.setString(4, task.getDestination());

            stmt.setDate(5, java.sql.Date.valueOf(task.getDeadline()));
            // JDBC doesn't directly support LocalDate. We will convert it in java.sql.Date

            stmt.setBoolean(6, task.isCompleted());

            int rows = stmt.executeUpdate();
            if (rows > 0) {System.out.println("Task has been saved successfully");}

        } catch (SQLException e){System.out.println("Error" + e.getMessage());}
    }

}
