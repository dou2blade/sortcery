package com.sortcery.backend.dto.user;

import com.sortcery.backend.model.User;

import java.util.Map;

public record UserStatsDTO(
    long total,
    Map<User.Role, Long> byRole
) {}
