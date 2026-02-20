package com.gifprojects.membersphere.services;

import com.gifprojects.membersphere.datatransfer.user.*;
import com.gifprojects.membersphere.model.Employee;
import com.gifprojects.membersphere.model.Manager;
import com.gifprojects.membersphere.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.gifprojects.membersphere.repository.IUserRepository;

@Service
public class UserService {
    private final IUserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    @Autowired
    public UserService(IUserRepository userRepository, BCryptPasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    public void registerAccount(UserRegistrationDTO data){
        /* Validating email */
        if(!isValidEmail(data.getEmail())){
            throw new RuntimeException("Email format is invalid");
        }
        if(userRepository.getUserByEmail(data.getEmail())!=null){
            throw new RuntimeException("Email already in use");
        }
        /* Validating phone */
        if(!isValidPhone(data.getPhone())){
            throw new RuntimeException("Phone number format is invalid");
        }
        /* Validating username */
        if (data.getUsername()!=null && !data.getUsername().isEmpty()) {
            if(data.getUsername().length() < 3){
                throw new RuntimeException("Username too short");
            }
            if(userRepository.getUserByUsername(data.getUsername()) != null){
                throw new RuntimeException("Username already taken");
            }
        }
        /* Validating password */
        if (data.getPassword()!=null && !data.getPassword().isEmpty()){
            if(data.getPassword().length() < 6){
                throw new RuntimeException("Password too weak");
            }
        }

        String hashedPassword = passwordEncoder.encode(data.getPassword());
        User newUser;

        if ("Manager".equalsIgnoreCase(data.getRole())){

            if (data.getManagerCode() == null || !data.getManagerCode().equals("test_code")){
                throw new IllegalStateException("Manager account code expired or is invalid. Access denied.");
            } else {
                newUser = Manager.createManager(data.getUsername(), hashedPassword, data.getEmail(), data.getPhone());
            }

        } else {
            newUser = Employee.createEmployee(data.getUsername(), hashedPassword, data.getEmail(), data.getPhone());
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

    public void updateAccount(UserUpdateDTO data){
        User user = userRepository.getUserById(data.getId());
        if (user == null)throw new RuntimeException("User not found");
        /* Validating email and uniquity */
        if (data.getEmail() != null && !data.getEmail().equals(user.getEmail())){
            if (!isValidEmail(data.getEmail())){
                throw new RuntimeException("Email format is invalid");
            }
            if (userRepository.getUserByEmail(data.getEmail()) != null){
                throw new RuntimeException("Email already taken");
            }
            user.setEmail(data.getEmail());
        }
        /* Validating phone number */
        if (data.getPhone() != null && !data.getPhone().equals(user.getPhone())){
            if (!isValidPhone(data.getPhone())){
                throw new RuntimeException("Phone number format is invalid");
            }
            user.setPhone(data.getPhone());
        }
        /* Validating username */
        if (data.getUsername()!=null && !data.getUsername().equals(user.getUsername())) {
            if(userRepository.getUserByUsername(data.getUsername()) != null){
                throw new RuntimeException("Username already taken");
            }
            if(data.getUsername().length() < 3){
                throw new RuntimeException("Username too short");
            }
            user.setUsername(data.getUsername());
        }
        /* Validating password */
        if (data.getPassword()!=null && !data.getPassword().isEmpty()){
            if(data.getPassword().length() < 6){
                throw new RuntimeException("Password too weak");
            }
            user.setPassword(data.getPassword());
        }

        userRepository.updateUser(user);
    }

    public void deleteAccount(long userId, long requesterId){

        User newUser = userRepository.getUserById(requesterId);
        if (newUser.getId() == userId){
            userRepository.deleteUserById(userId);
        } else {
            throw new IllegalStateException("Invalid request. Only the owner can delete the account.");
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
