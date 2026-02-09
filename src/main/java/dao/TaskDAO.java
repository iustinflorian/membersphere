package dao;

import com.gifprojects.membersphere.DatabaseConfig;
import model.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
            if (rows > 0) {
                System.out.println("Task has been saved successfully");
            } else {
                System.out.println("Task has not been saved");
            }
        } catch (SQLException e){
            System.out.println("Error" + e.getMessage());
        }
    }

    public static List<Task> getTasksByEmail(String email){
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks WHERE destination = ?";

        try (Connection conn = DatabaseConfig.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery();){
                while (rs.next()){
                    tasks.add(
                        Task.createTask(
                                rs.getString("title"),
                                rs.getString("details"),
                                rs.getString("source"),
                                rs.getString("destination"),
                                rs.getString("deadline"))
                    );
                }
            } catch (SQLException e){
                System.out.println("Error" + e.getMessage()+". No tasks available.");
            }
        } catch (SQLException e){
            System.out.println("Error" + e.getMessage());
        }

        return tasks;
    }

    public static void updateTask(Task task) {}

    public static void deleteTaskById(Long id){
        String sql = "DELETE FROM tasks WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setLong(1, id);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Task has been removed successfully");
            } else {
                System.out.println("No task found with the ID " + id);
            }
        } catch (SQLException e){
            System.out.println("Error" + e.getMessage());}
    }

}
