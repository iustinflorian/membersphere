package com.gifprojects.membersphere.services;

import com.gifprojects.membersphere.model.Employee;
import com.gifprojects.membersphere.model.Manager;
import com.gifprojects.membersphere.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.gifprojects.membersphere.repository.IUserRepository;

@Service
public class UserService {
    private final IUserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(IUserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerAccount(String username, String password, String email, String phone, String role){
        /* Validating email */
        if(!isValidEmail(email)){
            throw new IllegalStateException("Email format is invalid");
        }
        if(userRepository.getUserByEmail(email)!=null){
            throw new IllegalStateException("Email already in use");
        }
        /* Validating phone */
        if(!isValidPhone(phone)){
            throw new IllegalStateException("Phone number format is invalid");
        }
        /* Validating username */
        if (username!=null && !username.isEmpty()) {
            if(username.length() < 3){
                throw new IllegalStateException("Username too short");
            }
            if(userRepository.getUserByUsername(username) != null){
                throw new IllegalStateException("Username already taken");
            }
        }
        /* Validating password */
        if (password!=null && !password.isEmpty()){
            if(password.length() < 6){
                throw new IllegalStateException("Password too weak");
            }
        }

        String hashedPassword = passwordEncoder.encode(password);
        User newUser;

        if ("Manager".equalsIgnoreCase(role)){
            newUser = Manager.createManager(username, hashedPassword, email, phone);
        } else {
            newUser = Employee.createEmployee(username, hashedPassword, email, phone);
        }

        userRepository.saveUser(newUser);
    }

    public User loginAccount(String email, String password){
        User user = userRepository.getUserByEmail(email);
        if (user == null) throw new RuntimeException("User with email: " + email + "not found");

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Password is incorrect");
        }

        return user;
    }

    public void updateAccount(long id, String email, String username, String password, String phone){
        User user = userRepository.getUserById(id);
        if (user == null)throw new RuntimeException("User not found");
        /* Validating email and uniquity */
        if (email != null && !email.equals(user.getEmail())){
            if (!isValidEmail(email)){
                throw new IllegalStateException("Email format is invalid");
            }
            if (userRepository.getUserByEmail(email) != null){
                throw new IllegalStateException("Email already taken");
            }
            user.setEmail(email);
        }
        /* Validating phone number */
        if (phone != null && !phone.equals(user.getPhone())){
            if (!isValidPhone(phone)){
                throw new IllegalStateException("Phone number format is invalid");
            }
            user.setPhone(phone);
        }
        /* Validating username */
        if (username!=null && !username.equals(user.getUsername())) {
            if(userRepository.getUserByUsername(username) != null){
                throw new IllegalStateException("Username already taken");
            }
            if(username.length() < 3){
                throw new IllegalStateException("Username too short");
            }
            user.setUsername(username);
        }
        /* Validating password */
        if (password!=null && !password.isEmpty()){
            if(password.length() < 6){
                throw new IllegalStateException("Password too weak");
            }
            user.setPassword(password);
        }

        userRepository.updateUser(user);
    }

    public void deleteAccount(long id){
        boolean deleted = userRepository.deleteUserById(id);
        if (!deleted){
            throw new RuntimeException("User not found");
        }
    }

    /* FOR VALIDATION */
    private boolean isValidEmail(String email){
        String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email != null && email.matches(emailPattern);
    }

    private boolean isValidPhone(String phone) {
        String phonePattern = "^07\\d{8}$";
        return phone != null && phone.matches(phonePattern);
    }

}
