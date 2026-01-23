/*
for self-documentation:
DTOs - data moving (JSON)
only what frontend/user needs
no logic; only data storage
*/

package dto;

// outgoing data
public class UserResponseDTO {
    private Long id;
    private String fullName;

    private String username;
    private String email;

    private String memberSince;

    public UserResponseDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String  getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMemberSince() { return memberSince; }
}
