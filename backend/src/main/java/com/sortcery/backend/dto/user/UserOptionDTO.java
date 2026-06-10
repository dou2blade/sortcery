package com.sortcery.backend.dto.user;

import com.sortcery.backend.model.User;

public class UserOptionDTO {
    private final Long id;
    private final String email;

    public UserOptionDTO(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
    }

    public Long getId() { return id; }
    public String getEmail() { return email; }
}

