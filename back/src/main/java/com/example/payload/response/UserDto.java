package com.example.payload.response;

import java.util.Set;
import com.example.model.Role;

public record UserDto (
    String id,
    String firstname,
    String lastname,
    String username,
    String email,
    Set<Role> roles,
    String associationId
) {}