package com.smartSchool.services.impl;

import com.smartSchool.dtos.login.RegisterUserDto;
import com.smartSchool.entities.Role;
import com.smartSchool.entities.User;
import com.smartSchool.enums.RoleName;
import com.smartSchool.repositories.RoleRepository;
import com.smartSchool.repositories.UserRepository;
import com.smartSchool.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(RegisterUserDto dto) {
        RoleName enumRole = RoleName.valueOf(dto.getRole().toUpperCase());
        Role role = roleRepository.findByName(enumRole)
                .orElseThrow(() -> new RuntimeException("Role not found: " + enumRole));

        User user = User.builder()
                .fullName(dto.getFullName())
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(role)
                .active(true)
                .build();

        return userRepository.save(user);
    }

    @Override
    public List<User> allUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findByName(String name) {
        return userRepository.findByFullName(name)
                .orElseThrow(() -> new RuntimeException("User not found with name: " + name));
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }

    @Override
    public User findByIdentifier(String identifier) {
        return userRepository.findByEmailOrUsername(identifier)
                .orElseThrow(() -> new RuntimeException("User not found with email or username: " + identifier));
    }

    @Override
    public User updateUser(Integer id, User user) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        existing.setFullName(user.getFullName());
        existing.setUsername(user.getUsername());
        existing.setEmail(user.getEmail());

        if (user.getRole() != null) {
            existing.setRole(user.getRole());
        }

        return userRepository.save(existing);
    }

    @Override
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public User updatePasswordByEmail(String email, String newPassword) {
        User user = findByEmail(email);
        user.setPassword(passwordEncoder.encode(newPassword));
        return userRepository.save(user);
    }

    @Override
    public User updateUserRole(Integer id, String roleName) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        RoleName enumRole = RoleName.valueOf(roleName.toUpperCase());
        Role role = roleRepository.findByName(enumRole)
                .orElseThrow(() -> new RuntimeException("Role not found: " + enumRole));

        user.setRole(role);
        return userRepository.save(user);
    }

    @Override
    public List<User> searchUsers(String keyword) {
        return userRepository.findByFullNameContainingIgnoreCaseOrEmailContainingIgnoreCase(keyword, keyword);
    }

    @Override
    public User deactivateUser(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        user.setActive(false);
        return userRepository.save(user);
    }

    @Override
    public User reactivateUser(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        user.setActive(true);
        return userRepository.save(user);
    }
}