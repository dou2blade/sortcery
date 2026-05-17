package com.sortcery.backend.dto.auth;

import com.sortcery.backend.model.User;

public class LoginResponseDTO {
    private Long id;
    private String email;
    private User.Role role;
    private String plainToken;

    public LoginResponseDTO(User user, String plainToken) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.plainToken = plainToken;
    }

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public User.Role getRole() { return role; }
    public String getPlainToken() { return plainToken; }
}
