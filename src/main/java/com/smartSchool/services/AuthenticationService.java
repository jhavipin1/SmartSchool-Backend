package com.smartSchool.services;

import com.smartSchool.dtos.login.LoginUserDto;
import com.smartSchool.entities.User;
import com.smartSchool.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager
    ) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
    }

    public User authenticate(LoginUserDto input) {
        // Authenticates against password using either email or username as principal
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getIdentifier(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmailOrUsername(input.getIdentifier())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}