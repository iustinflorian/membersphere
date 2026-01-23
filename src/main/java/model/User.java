/*
for self-documentation:
model - mapped directly to database table
includes sensitive data, metadata etc.

!!!! used lombok to not create getters/setters individually
*/

package model;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class User {
    private Long id;
    private String username;
    private String email;

    private String lastName;
    private String firstName;

    private boolean isEmployee;
    /*
     if the user is also an employee
     if not = limited system_access
    */

    private String passwordHash;
    private LocalDateTime createdAt;

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }
}
