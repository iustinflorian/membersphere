package service;

import model.Employee;
import model.Manager;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.IUserRepository;

@Service
public class UserService {
    private final IUserRepository userRepository;

    @Autowired
    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(String username, String password, String email, String phone, String role){
        User newUser;

        if ("MANAGER".equalsIgnoreCase(role)){
            newUser = Manager.createManager(username, password, email, phone);
        } else {
            newUser = Employee.createEmployee(username, password, email, phone);
        }

        userRepository.saveUser(newUser);
    }

    public User loginUser(String username, String password){
        User user = userRepository.getUserByUsername(username);

        if (user != null && user.getPassword().equals(password)){
            return user;
        }
        return null;
    }

    public boolean deleteAccount(long id){
        userRepository.deleteUserById(id);
        return true;
    }

}
