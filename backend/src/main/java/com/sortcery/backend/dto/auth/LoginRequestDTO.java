package com.sortcery.backend.dto.auth;

import com.sortcery.backend.validation.Create;

import jakarta.validation.constraints.NotBlank;

public class LoginRequestDTO {

    @NotBlank(groups=Create.class)
    private String email;

    @NotBlank(groups=Create.class)
    private String password;

    public String getEmail() { return email; }
    public String getPassword() { return password; }
    
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
}
