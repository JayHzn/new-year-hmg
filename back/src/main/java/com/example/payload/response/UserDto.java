package com.example.payload.response;

import java.util.Set;

public record UserDto (
    String id,
    String firstname,
    String lastname,
    String username,
    String email,
    Set<String> roles,
    String associationId
) {}