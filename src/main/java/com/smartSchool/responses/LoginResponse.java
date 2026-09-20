package com.smartSchool.responses;

public class LoginResponse {
    private String token;
    private long expiresIn;
    private String fullName;
    private String username;
    private String role;
    private String roleDescription;

    public String getToken() { return token; }
    public LoginResponse setToken(String token) { this.token = token; return this; }

    public long getExpiresIn() { return expiresIn; }
    public LoginResponse setExpiresIn(long expiresIn) { this.expiresIn = expiresIn; return this; }

    public String getFullName() { return fullName; }
    public LoginResponse setFullName(String fullName) { this.fullName = fullName; return this; }

    public String getUsername() { return username; }
    public LoginResponse setUsername(String username) { this.username = username; return this; }

    public String getRole() { return role; }
    public LoginResponse setRole(String role) { this.role = role; return this; }

    public String getRoleDescription() { return roleDescription; }
    public LoginResponse setRoleDescription(String roleDescription) { this.roleDescription = roleDescription; return this; }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "token='" + token + '\'' +
                ", expiresIn=" + expiresIn +
                ", fullName='" + fullName + '\'' +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                ", roleDescription='" + roleDescription + '\'' +
                '}';
    }
}