package com.sortcery.backend.dto.user;

import com.sortcery.backend.model.User;
import com.sortcery.backend.validation.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;

public class UserRequestDTO {

    @NotBlank(groups=Create.class)
    private String firstName;

    private String middleName;

    @NotBlank(groups=Create.class)
    private String lastName;

    @NotBlank(groups=Create.class)
    @Email
    private String email;

    @NotBlank(groups=Create.class)
    private String password;

    @NotNull(groups=Create.class)
    private User.Role role;

    public String getFirstName() { return firstName; }
    public String getMiddleName() { return middleName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public User.Role getRole() { return role; }

    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(User.Role role) { this.role = role; }
}
