package com.gifprojects.membersphere.controller;

import com.gifprojects.membersphere.datatransfer.UserDeleteDTO;
import com.gifprojects.membersphere.datatransfer.UserLoginDTO;
import com.gifprojects.membersphere.datatransfer.UserRegistrationDTO;
import com.gifprojects.membersphere.datatransfer.UserUpdateDTO;
import com.gifprojects.membersphere.model.User;
import com.gifprojects.membersphere.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")

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

            userService.registerAccount(username, password, email, phone, role);
            return new ResponseEntity<>("User registered successfully!", HttpStatus.CREATED);
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error" + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody UserLoginDTO data){
        try{
            User user = userService.loginAccount(data.getEmail(), data.getPassword());
            return ResponseEntity.ok(user);
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error" + e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> update (@RequestBody UserUpdateDTO data){
        try{
            userService.updateAccount(
                data.getId(),
                data.getEmail(),
                data.getUsername(),
                data.getPassword(),
                data.getPhone()
            );
            return ResponseEntity.ok("User updated succesfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error" + e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete (@RequestBody UserDeleteDTO data){
        try{
            userService.deleteAccount(data.getId());
            return new ResponseEntity<>("User deleted successfully!", HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error" + e.getMessage());
        }
    }



}
