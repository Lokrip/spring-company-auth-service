package com.cloud.app.spring_company_auth_serivce.dto;

//record всегда final — нельзя наследоваться.
public record UserDTO(
    String username,
    String email,
    String password,
    String passwordConfirmation
    // equals(), hashCode(), toString() автоматически реализуются
) {
    public UserDTO {
        if (!password.equals(passwordConfirmation)) {
            throw new IllegalArgumentException("Passwords do not match");
        }
    }
}

