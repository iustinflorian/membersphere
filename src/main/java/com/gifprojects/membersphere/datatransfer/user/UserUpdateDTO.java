package com.gifprojects.membersphere.datatransfer.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateDTO {
    private String username;
    private String email;
    private String password;
    private String phone;
    private long id;
}
