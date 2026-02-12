package com.gifprojects.membersphere.repository;

import com.gifprojects.membersphere.DatabaseConfig;
import com.gifprojects.membersphere.model.Employee;
import com.gifprojects.membersphere.model.Manager;
import com.gifprojects.membersphere.model.User;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class JdbcUserRepository implements IUserRepository {

    @Override
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
            stmt.executeUpdate();
        } catch (SQLException e){
            System.out.println("Error" + e.getMessage());
        }
    }

    @Override
    public User getUserById(long id){
        String sql = "SELECT * FROM users WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()){
                if (rs.next()){
                    String _role = rs.getString("role");
                    String _username = rs.getString("username");
                    String _password = rs.getString("password");
                    String _email = rs.getString("email");
                    String _phone = rs.getString("phone");
                    long _id = rs.getLong("id");

                    User user;
                    if ("Manager".equals(_role)){
                        user = Manager.createManager(_username, _password, _email, _phone);
                    } else {
                        user = Employee.createEmployee(_username, _password, _email, _phone);
                    }
                    user.setId(_id);

                    return user;
                }
            }
        } catch (SQLException e ){
            System.out.println("Error" + e.getMessage());
        }
        return null;
    }

    @Override
    public User getUserByEmail(String email){
        String sql = "SELECT * FROM users WHERE email = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery())
            {
                if (rs.next()){
                    String _role = rs.getString("role");
                    String _username = rs.getString("username");
                    String _password = rs.getString("password");
                    String _email = rs.getString("email");
                    String _phone = rs.getString("phone");
                    long _id = rs.getLong("id");

                    User user;
                    if ("Manager".equals(_role)){
                        user = Manager.createManager(_username, _password, _email, _phone);
                    } else {
                        user = Employee.createEmployee(_username, _password, _email, _phone);
                    }
                    user.setId(_id);

                    return user;
                }
            }
        }
        catch (SQLException e){
            System.out.println("Error" + e.getMessage());
        }
        return null;
    }

    @Override
    public void updateUser(User user){
        String sql = "UPDATE users SET username = ?, password = ?, email = ?, phone = ? WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPhone());
            stmt.setLong(5, user.getId());

            stmt.executeUpdate();
        } catch (SQLException e){
            throw new RuntimeException("Eroare la baza de date: " + e.getMessage());
        }
    }

    @Override
    public boolean deleteUserById(long id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setLong(1, id);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e){
            throw new RuntimeException("Eroare la baza de date: " + e.getMessage());
        }
    }

}
