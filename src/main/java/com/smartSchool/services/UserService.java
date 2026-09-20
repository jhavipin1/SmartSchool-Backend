package com.smartSchool.services;

import com.smartSchool.dtos.login.RegisterUserDto;
import com.smartSchool.entities.User;

import java.util.List;

public interface UserService {
    User createUser(RegisterUserDto dto);
    List<User> allUsers();
    User findByName(String name);
    User findByEmail(String email);
    User findByUsername(String username);
    User findByIdentifier(String identifier);
    User updateUser(Integer id, User user);
    void deleteUser(Integer id);
    User updatePasswordByEmail(String email, String newPassword);
    User updateUserRole(Integer id, String role);
    List<User> searchUsers(String keyword);
    User deactivateUser(Integer id);
    User reactivateUser(Integer id);
}