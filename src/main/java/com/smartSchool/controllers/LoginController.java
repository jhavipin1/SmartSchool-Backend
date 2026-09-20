package com.smartSchool.controllers;

import com.smartSchool.dtos.login.LoginUserDto;
import com.smartSchool.dtos.login.ResetPasswordDto;
import com.smartSchool.entities.User;
import com.smartSchool.responses.LoginResponse;
import com.smartSchool.services.AuthenticationService;
import com.smartSchool.services.JwtService;
import com.smartSchool.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    private final UserService userService;

    public LoginController(JwtService jwtService,
                           AuthenticationService authenticationService,
                           UserService userService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    // Login
    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);

        String roleDescription = authenticatedUser.getRole().getDescription();
        // Fix: Direct String getter from BaseLookupEntity
        String roleName = authenticatedUser.getRole().getName().name();

        LoginResponse loginResponse = new LoginResponse()
                .setToken(jwtToken)
                .setExpiresIn(jwtService.getExpirationTime())
                .setFullName(authenticatedUser.getFullName())
                .setUsername(authenticatedUser.getUsername())
                .setRole(roleName)
                .setRoleDescription(roleDescription);

        return ResponseEntity.ok(loginResponse);
    }

    // Reset password (ADMIN / SUPER_ADMIN only)
    @PutMapping("/auth/reset-password/{email}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<User> resetPassword(@PathVariable String email,
                                              @RequestBody ResetPasswordDto dto) {
        return ResponseEntity.ok(userService.updatePasswordByEmail(email, dto.getNewPassword()));
    }

    // Get authenticated user
    @GetMapping("/users/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<User> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        return ResponseEntity.ok(currentUser);
    }

    // Get all users
    @GetMapping("/users")
    //@PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<List<User>> allUsers() {
        return ResponseEntity.ok(userService.allUsers());
    }

    // Get user by name
    @GetMapping("/users/by-name/{name}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<User> getUserByName(@PathVariable String name) {
        return ResponseEntity.ok(userService.findByName(name));
    }

    // Get user by email
    @GetMapping("/users/by-email/{email}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.findByEmail(email));
    }

    // Update user
    @PutMapping("/users/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/users/{id}/role")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<User> changeUserRole(@PathVariable Integer id, @RequestParam String role) {
        return ResponseEntity.ok(userService.updateUserRole(id, role));
    }

    @GetMapping("/users/search")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<List<User>> searchUsers(@RequestParam String keyword) {
        return ResponseEntity.ok(userService.searchUsers(keyword));
    }

    @PutMapping("/users/{id}/deactivate")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<User> deactivateUser(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.deactivateUser(id));
    }

    @PutMapping("/users/{id}/reactivate")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<User> reactivateUser(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.reactivateUser(id));
    }

    @PutMapping("/auth/change-password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<User> changePassword(@RequestBody ResetPasswordDto dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        return ResponseEntity.ok(userService.updatePasswordByEmail(currentUser.getEmail(), dto.getNewPassword()));
    }
}