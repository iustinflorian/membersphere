package repository;

import model.User;

public interface IUserRepository {
    void saveUser(User user);
    User getUserByUsername(String username);
    User getUserByEmail(String email);
    void updateUser(User user);
    void deleteUserById(long id);
}
