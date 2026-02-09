package dao;

import com.gifprojects.membersphere.DatabaseConfig;
import model.Employee;
import model.Manager;
import model.User;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public void saveUser(User user) {
        String sql = "INSERT INTO users (username, password, email, phone, role) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPhone());
            if(user instanceof Manager){
                stmt.setString(5, "Manager");
            }
            else {
                stmt.setString(5, "Employee");
            }
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("User has been saved successfully");
            } else {
                System.out.println("User has not been saved");
            }
        } catch (SQLException e){
            System.out.println("Error" + e.getMessage());
        }
    }

    public User getUserByUsername(String username){
        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = DatabaseConfig.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery())
            {
                if (rs.next()){
                    String _role = rs.getString("role");
                    String _username = rs.getString("username");
                    String _password = rs.getString("password");
                    String _email = rs.getString("email");
                    String _phone = rs.getString("phone");
                    if ("Manager".equals(_role)){
                        return Manager.createManager(_username, _password, _email, _phone);
                    } else {
                        return Employee.createEmployee(_username, _password, _email, _phone);
                    }
                }
            }
        }
        catch (SQLException e){
            System.out.println("Error" + e.getMessage());
        }
        return null;
    }

    public void updateUser(User user) {
        String sql = "UPDATE users SET username = ?, password = ?, email = ?, phone = ? WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPhone());
            stmt.setLong(5, user.getId());

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("User updated successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void deleteUserById(Long id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setLong(1, id);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("User has been deleted successfully");
            }
            else {
                System.out.println("No user found with the ID " + id);
            }
        } catch (SQLException e){
            System.out.println("Error" + e.getMessage());
        }
    }

}
