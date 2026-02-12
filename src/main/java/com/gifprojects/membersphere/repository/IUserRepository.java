package com.gifprojects.membersphere.repository;

import com.gifprojects.membersphere.model.User;

public interface IUserRepository {
    void saveUser(User user);
    User getUserById(long id);
    User getUserByEmail(String email);
    void updateUser(User user);
    boolean deleteUserById(long id);
}
