package com.sortcery.backend.dto.user;

import com.sortcery.backend.model.User;

public record RoleCountDTO(
    User.Role role,
    long count
) {}
