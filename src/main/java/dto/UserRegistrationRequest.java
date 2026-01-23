/*
for self-documentation:
DTOs - data moving (JSON)
only what frontend/user needs
no logic; only data storage
*/

package dto;

// incoming data
public class UserRegistrationRequest {
    private String username;
    private String password; // not hash
    private String email;

    private String firstName;
    private String lastName;

    public UserRegistrationRequest() {}

    public String getUsername() { return username; }
    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword() { return password; }
    public void setPassword(String password){
        this.password = password;
    }

    public String getEmail() { return email; }
    public void setEmail(String email){
        this.email = email;
    }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
}
