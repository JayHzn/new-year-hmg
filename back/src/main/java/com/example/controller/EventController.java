package com.example.controller;

import com.example.payload.request.EventCreateRequest;
import com.example.payload.request.EventUpdateRequest;
import com.example.payload.response.EventDto;
import com.example.model.EventStatus;
import com.example.model.EventVisibility;
import com.example.service.EventService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {
    private final EventService eventService;
    public EventController(EventService eventService) { this.eventService = eventService; }

    @GetMapping
    public List<EventDto> listAll() { return eventService.listAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<EventDto> getById(@PathVariable String id) {
        return ResponseEntity.ok(eventService.getById(id));
    }

    @GetMapping("/by-organization/{orgId}")
    public List<EventDto> listByOrganization(@PathVariable String orgId) {
        return eventService.listByOrganizationId(orgId);
    }

    @GetMapping("/between")
    public List<EventDto> between(@RequestParam Instant start, @RequestParam Instant end) {
        return eventService.listBetween(start, end);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','ORGANIZER')")
    public ResponseEntity<EventDto> create(@Valid @RequestBody EventCreateRequest req) {
        return ResponseEntity.status(201).body(eventService.create(req));
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN','ORGANIZER')")
    public ResponseEntity<EventDto> update(@PathVariable String id, @Valid @RequestBody EventUpdateRequest req) {
        return ResponseEntity.ok(eventService.update(id, req));
    }

    @DeleteMapping
    @PreAuthorize("hasAnyRole('ADMIN','ORGANIZER')")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        eventService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
