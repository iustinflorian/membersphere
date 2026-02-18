package com.gifprojects.membersphere.repository;

import com.gifprojects.membersphere.DatabaseConfig;
import com.gifprojects.membersphere.model.Task;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcTaskRepository implements ITaskRepository{

    @Override
    public void saveTask(Task task) {
        String sql = "INSERT INTO tasks (title, details, source, destination, source_id, destination_id, deadline, completed) " +
                "VALUES (?, ?, ?, ?," +
                "(SELECT id FROM users WHERE email = ?)," +
                "(SELECT id FROM users WHERE email = ?)," +
                "?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDetails());
            stmt.setString(3, task.getSource());
            stmt.setString(4, task.getDestination());

            stmt.setString(5, task.getSource());
            stmt.setString(6, task.getDestination());

            stmt.setDate(7, java.sql.Date.valueOf(task.getDeadline()));
            // JDBC doesn't directly support LocalDate. We will convert it in java.sql.Date
            stmt.setBoolean(8, task.isCompleted());

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

    @Override
    public List<Task> getTasksById(long id){
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks WHERE source_id = ? OR destination_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setLong(1, id);
            stmt.setLong(2, id);
            try(ResultSet rs = stmt.executeQuery()){
                while (rs.next()){
                    tasks.add(
                        Task.createTask(
                                rs.getString("title"),
                                rs.getString("details"),
                                rs.getString("source"),
                                rs.getString("destination"),
                                rs.getDate("deadline").toString())
                    );
                }
            }
        } catch (SQLException e){
            System.out.println("Error" + e.getMessage()+". No tasks available.");
        }
        return tasks;
    }

    @Override
    public void updateTask(long taskId, boolean isCompleted) {
        String sql = "UPDATE tasks SET completed = ? WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setBoolean(1, isCompleted);
            stmt.setLong(2, taskId);
            int rows = stmt.executeUpdate();
            if (rows > 0){
                System.out.println("Task has been updated successfully");
            } else {
                System.out.println("Task has not been updated");
            }
        } catch (SQLException e){
            System.out.println("Error" + e.getMessage()+". No tasks available.");
        }
    }

    @Override
    public void deleteTaskById(Long id){
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

    /*
    @Override
    public List<Task> getTasksByEmail(String email){
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks WHERE destination = ?";

        try (Connection conn = DatabaseConfig.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()){
                while (rs.next()){
                    tasks.add(
                        Task.createTask(
                                rs.getString("title"),
                                rs.getString("details"),
                                rs.getString("source"),
                                rs.getString("destination"),
                                rs.getDate("deadline").toString())
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
*/
}
