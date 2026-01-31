package com.library.managementsystem.model.user;

import com.library.managementsystem.model.user.UserRole;

/**
 * Outgoing response for user data.
 * Contains NO password or password hash.
 */
public record UserResponse(

        Integer id,
        String name,
        String email,
        UserRole role
) {}
