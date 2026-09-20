package com.smartSchool.dtos.login;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)   // 🔹 enables fluent setters
public class RegisterUserDto {
    private String fullName;
    private String username;
    private String email;
    private String password;
    private String role;
}