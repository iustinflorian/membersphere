package com.gifprojects.membersphere.repository;

import com.gifprojects.membersphere.model.User;

public interface IUserRepository {
    void saveUser(User user);
    User getUserByUsername(String username);
    User getUserByEmail(String email);
    void deleteUserById(long id);
}
