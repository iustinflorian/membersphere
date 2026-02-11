package com.gifprojects.membersphere.controller;

import com.gifprojects.membersphere.dtobject.UserLoginDTO;
import com.gifprojects.membersphere.dtobject.UserRegistrationDTO;
import com.gifprojects.membersphere.model.User;
import com.gifprojects.membersphere.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users/")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register (@RequestBody UserRegistrationDTO data){
        try{
            String username = data.getUsername();
            String password = data.getPassword();
            String email = data.getEmail();
            String phone = data.getPhone();
            String role = data.getRole();

            userService.registerUser(username, password, email, phone, role);
            return new ResponseEntity<>("User registered successfully!", HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>("Error: "+e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody UserLoginDTO data){
        try{
            User user = userService.loginUser(data.getUsername(), data.getPassword());

            if (user != null){
                return ResponseEntity.ok(user);
            } else {
                return new ResponseEntity<>("Username or password is incorrect.", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e){
            return new ResponseEntity<>("Error: "+e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
