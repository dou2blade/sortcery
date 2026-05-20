package com.sortcery.backend.dto.auth;

import com.sortcery.backend.model.User;

public class LoginResponseDTO {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private User.Role role;
    private String plainToken;

    public LoginResponseDTO(User user, String plainToken) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.role = user.getRole();
        this.plainToken = plainToken;
    }

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public User.Role getRole() { return role; }
    public String getPlainToken() { return plainToken; }
}
