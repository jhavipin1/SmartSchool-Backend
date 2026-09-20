package com.smartSchool.dtos.login;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserDto {
    private String identifier; // Accepts either email or username
    private String password;
}