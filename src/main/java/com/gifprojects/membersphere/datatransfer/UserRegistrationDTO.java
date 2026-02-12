package com.gifprojects.membersphere.datatransfer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationDTO {
    private String username;
    private String password;
    private String email;
    private String phone;
    private String role;
}
