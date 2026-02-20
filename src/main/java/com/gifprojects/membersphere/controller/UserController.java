package com.gifprojects.membersphere.controller;

import com.gifprojects.membersphere.datatransfer.user.*;
import com.gifprojects.membersphere.model.User;
import com.gifprojects.membersphere.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")

@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register (@RequestBody UserRegistrationDTO data){
        try{
            userService.registerAccount(data);
            return new ResponseEntity<>("User registered successfully!", HttpStatus.CREATED);
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody UserLoginDTO data){
        try{
            User user = userService.loginAccount(data.getEmail(), data.getPassword());
            return ResponseEntity.ok(user);
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PatchMapping("/update")
    public ResponseEntity<?> update (@RequestBody UserUpdateDTO data){
        try{
            userService.updateAccount(data);
            return ResponseEntity.ok("User updated succesfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<String> delete (@PathVariable long userId, @RequestHeader("X-User-Id") long requesterId){
        try{
            userService.deleteAccount(userId, requesterId);
            return new ResponseEntity<>("User deleted successfully!", HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

}