package com.gifprojects.membersphere.repository;

import com.gifprojects.membersphere.DatabaseConfig;
import com.gifprojects.membersphere.model.Task;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcTaskRepository implements ITaskRepository{

    @Override
    public boolean saveTask(Task task) {
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

            // for ids
            stmt.setString(5, task.getSource());
            stmt.setString(6, task.getDestination());

            stmt.setDate(7, Date.valueOf(task.getDeadline()));
            // JDBC doesn't directly support LocalDate. We will convert it in java.sql.Date
            stmt.setBoolean(8, task.isCompleted());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e){
            throw new RuntimeException("Critical database error.", e);
        }
    }

    @Override
    public List<Task> getTasksByUserId(long id){
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks WHERE source_id = ? OR destination_id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setLong(1, id);
            stmt.setLong(2, id);
            try(ResultSet rs = stmt.executeQuery()){
                while (rs.next()){
                    Task newTask = Task.createTask(
                            rs.getString("title"),
                            rs.getString("details"),
                            rs.getString("source"),
                            rs.getString("destination"),
                            rs.getDate("deadline").toString());
                    newTask.setId(rs.getLong("id"));
                    newTask.setCompleted(rs.getBoolean("completed"));
                    tasks.add(newTask);
                }
            }
        } catch (SQLException e){
            throw new RuntimeException("Critical database error.", e);
        }
        return tasks;
    }

    @Override
    public boolean updateTask(long taskId) {
        String sql = "UPDATE tasks SET completed = NOT completed WHERE id = ?";
        return byTaskId(taskId, sql);
    }

    @Override
    public boolean deleteTaskById(long taskId){
        String sql = "DELETE FROM tasks WHERE id = ?";
        return byTaskId(taskId, sql);
    }

    private boolean byTaskId(long taskId, String sql){
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setLong(1, taskId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e){
            throw new RuntimeException("Critical database error.", e);
        }
    }
}
