package com.gifprojects.membersphere.services;

import com.gifprojects.membersphere.model.Employee;
import com.gifprojects.membersphere.model.Manager;
import com.gifprojects.membersphere.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gifprojects.membersphere.repository.IUserRepository;

@Service
public class UserService {
    private final IUserRepository userRepository;

    @Autowired
    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerAccount(String username, String password, String email, String phone, String role){
        User newUser;

        if ("MANAGER".equalsIgnoreCase(role)){
            newUser = Manager.createManager(username, password, email, phone);
        } else {
            newUser = Employee.createEmployee(username, password, email, phone);
        }

        userRepository.saveUser(newUser);
    }

    public User loginAccount(String email, String password){
        User user = userRepository.getUserByEmail(email);

        if (user == null) throw new RuntimeException("User with email: " + email + "not found");
        if (!user.getPassword().equals(password)) throw new RuntimeException("Password is incorrect!");

        return user;
    }

    public void updateAccount(long id, String email, String username, String password, String phone){
        User user = userRepository.getUserById(id);

        if (email!=null) user.setEmail(email);
        if (username!=null) user.setUsername(username);
        if (password!=null) user.setPassword(password);
        if (phone!=null) user.setPhone(phone);

        userRepository.updateUser(user);
    }

    public void deleteAccount(long id){
        boolean deleted = userRepository.deleteUserById(id);
        if (!deleted){
            throw new RuntimeException("User not found");
        }
    }

}
