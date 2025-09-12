package com.example.service;

import com.example.model.Event;
import com.example.exception.RessourceNotFoundException;
import com.example.payload.request.EventCreateRequest;
import com.example.payload.request.EventUpdateRequest;
import com.example.payload.response.EventDto;
import com.example.repository.EventRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EventService {
    private final EventRepository eventRepo;

    public EventService(EventRepository eventRepo) {
        this.eventRepo = eventRepo;
    }

    public EventDto create(@Valid EventCreateRequest req) {
        validateDates(req.getStartDateTime(), req.getEndDateTime());

        Event e = new Event();
        e.setTitle(req.getTitle());
        e.setDescription(req.getDescription());
        e.setStartDateTime(req.getStartDateTime());
        e.setEndDateTime(req.getEndDateTime());
        e.setLocationName(req.getLocationName());
        e.setAddress(req.getAddress());
        e.setOrganizationId(req.getOrganizationId());
        e.setOrganizerUserId(req.getOrganizerUserId());
        e.setCapacity(req.getCapacity());
        e.setTags(req.getTags());
        e.setStatus(req.getStatus());
        e.setVisibility(req.getVisibility());
        return toDto(eventRepo.save(e));
    }

    public List<EventDto> listAll() {
        return eventRepo.findAll().stream().map(this::toDto).toList();
    }

    public EventDto getById(String id) {
        return toDto(eventRepo.findById(id)
            .orElseThrow(() -> new RessourceNotFoundException("Event not found : " + id)));
    }

    public EventDto update(String id, @Valid EventUpdateRequest req) {
        Event current = eventRepo.findById(id)
            .orElseThrow(() -> new RessourceNotFoundException("Event not found" + id));

        validateDates(req.getStartDateTime() != null ? req.getStartDateTime() : current.getStartDateTime(),
            req.getEndDateTime() != null ? req.getEndDateTime() : current.getEndDateTime());
        
        if (req.getTitle() != null) current.setTitle(req.getTitle());
        if (req.getDescription() != null) current.setDescription(req.getDescription());
        if (req.getStartDateTime() != null) current.setStartDateTime(req.getStartDateTime());
        if (req.getEndDateTime() != null) current.setEndDateTime(req.getEndDateTime());
        if (req.getLocationName() != null) current.setLocationName(req.getLocationName());
        if (req.getAddress() != null) current.setAddress(req.getAddress());
        if (req.getOrganizationId() != null) current.setOrganizationId(req.getOrganizationId());
        if (req.getOrganizerUserId() != null) current.setOrganizerUserId(req.getOrganizerUserId());
        if (req.getCapacity() != null) current.setCapacity(req.getCapacity());
        if (req.getTags() != null) current.setTags(req.getTags());
        if (req.getStatus() != null) current.setStatus(req.getStatus());
        if (req.getVisibility() != null) current.setVisibility(req.getVisibility());

        return toDto(eventRepo.save(current));
    }

    public void delete(String id) {
        if (!eventRepo.existsById(id)) {
            throw new RessourceNotFoundException("Event not found : " + id);
        }

        eventRepo.deleteById(id);
    }

    public List<EventDto> listByOrganizationId(String orgId) {
        return eventRepo.findByOrganizationId(orgId).stream().map(this::toDto).toList();
    }

    public List<EventDto> listBetween(Instant start, Instant end) {
        if (start == null || end == null || end.isBefore(start)) {
            throw new IllegalArgumentException("Invalid time range");
        }
        return eventRepo.findByStartDateTimeBetween(start, end).stream().map(this::toDto).toList();
    }

    private void validateDates(Instant start, Instant end) {
        if (start == null) return;
        if (end != null && end.isBefore(start)) {
            throw new IllegalArgumentException("endDateTime must be >= startDateTime");
        }
    }

    private EventDto toDto(Event e) {
        return new EventDto(
            e.getId(), e.getTitle(), e.getDescription(),
            e.getStartDateTime(), e.getEndDateTime(),
            e.getLocationName(), e.getAddress(),
            e.getOrganizationId(), e.getOrganizerUserId(),
            e.getCapacity(), e.getTags(),
            e.getStatus(), e.getVisibility(),
            e.getCreatedAt(), e.getUpdatedAt()
        );
    }
}
