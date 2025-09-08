package com.example.payload.response;

import java.time.Instant;

public record OrganizationDto (
    String id,
    String name,
    String description,
    String address,
    String logoUrl,
    String website,
    String ownerUserId,
    Instant createdAt,
    Instant updatedAt
) {}
