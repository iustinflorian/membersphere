package dao;

import com.gifprojects.membersphere.DatabaseConfig;
import model.Manager;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDAO {

    public static void saveUser(User user) {
        String sql = "INSERT INTO users (username, password, email, phone, role) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPhone());

            if(user instanceof Manager){
                stmt.setString(5, "Manager");
            } else {
                stmt.setString(5, "Employee");
            }

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("User has been saved successfully");
            }

        } catch (SQLException e){
            System.out.println("Error" + e.getMessage());
        }
    }

    public static void removeUser(User user) {
        String sql = "DELETE FROM users WHERE username = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, user.getUsername());

            int rowsDeleted = stmt.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("User has been deleted successfully");
            } else {
                System.out.println("No user found with the username " + user.getUsername());
            }

        } catch (SQLException e){
            System.out.println("Error" + e.getMessage());
        }
    }

}
