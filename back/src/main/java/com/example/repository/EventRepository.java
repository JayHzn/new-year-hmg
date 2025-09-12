package com.example.repository;

import com.example.model.Event;
import com.example.model.EventStatus;
import com.example.model.EventVisibility;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.time.Instant;
import java.util.List;

public interface EventRepository extends MongoRepository<Event, String> {
    List<Event> findByOrganizationId(String organizationId);
    List<Event> findByOrganizerUserId(String organizerUserId);
    List<Event> findByStatus(EventStatus status);
    List<Event> findByVisibility(EventVisibility visibility);
    List<Event> findByStartDateTimeBetween(Instant start, Instant end);
}
