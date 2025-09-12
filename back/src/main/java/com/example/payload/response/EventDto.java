package com.example.payload.response;

import java.time.Instant;
import java.util.Set;

import com.example.model.EventStatus;
import com.example.model.EventVisibility;

public record EventDto (
    String id,
    String title,
    String description,
    Instant startDateTime,
    Instant endDateTime,
    String locationName,
    String address,
    String organizationId,
    String organizerUserId,
    Integer capacity,
    Set<String> tags,
    EventStatus status,
    EventVisibility visibility,
    Instant createdAt,
    Instant updatedAt
) {}
