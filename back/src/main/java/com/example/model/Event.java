package com.example.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.Set;

@Document(collection = "events")
public class Event {

    @Id
    private String id;

    @NotBlank @Size(min = 2, max = 160)
    private String title;

    @Size(max = 5000)
    private String description;

    @NotNull
    private Instant startDateTime;

    private Instant endDateTime;

    @Size(max = 160)
    private String locationName;

    @Size(max = 255)
    private String address;

    @Indexed
    private String organizationId;

    @Indexed
    private String organizerUserId;

    private Integer capacity;

    private Set<String> tags;

    @NotNull
    private EventStatus status = EventStatus.DRAFT;

    @NotNull
    private EventVisibility visibility = EventVisibility.PUBLIC;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    public Event() {}

    // --Getters & Setters--
    public String getId() { return id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Instant getStartDateTime() { return startDateTime; }
    public void setStartDateTime(Instant startDateTime) { this.startDateTime = startDateTime; }

    public Instant getEndDateTime() { return endDateTime; }
    public void setEndDateTime(Instant endDateTime) { this.endDateTime = endDateTime; }

    public String getLocationName() { return locationName; }
    public void setLocationName(String locationName) {this.locationName = locationName; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getOrganizationId() { return organizationId; }
    public void setOrganizationId(String organizationId) {this.organizationId = organizationId; }

    public String getOrganizerUserId() { return organizerUserId; }
    public void setOrganizerUserId(String organizerUserId) { this.organizerUserId = organizerUserId; }

    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }

    public Set<String> getTags() { return tags; }
    public void setTags(Set<String> tags) { this.tags = tags; }

    public EventStatus getStatus() { return status; }
    public void setStatus(EventStatus status) { this.status = status; }

    public EventVisibility getVisibility() { return visibility; }
    public void setVisibility(EventVisibility visibility) { this.visibility = visibility; }

    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }

}
