package com.example.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.time.Instant;
import java.util.Set;
import com.example.model.EventStatus;
import com.example.model.EventVisibility;

public class EventCreateRequest {
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

    private String organizationId;

    private String organizerUserId;

    private Integer capacity;

    private Set<String> tags;

    @NotNull
    private EventStatus status = EventStatus.DRAFT;

    @NotNull
    private EventVisibility visibility = EventVisibility.PUBLIC;

    public String getTitle() { return title; }
    public void setTitle(String v) { title = v; }

    public String getDescription() { return description; }
    public void setDescription(String v) { description = v; }

    public Instant getStartDateTime() { return startDateTime; }
    public void setStartDateTime(Instant v) { startDateTime = v; }

    public Instant getEndDateTime() { return endDateTime; }
    public void setEndDateTime(Instant v) { endDateTime = v; }

    public String getLocationName() { return locationName; }
    public void setLocationName(String v) { locationName = v; }

    public String getAddress() { return address; }
    public void setAddress(String v) { address = v; }

    public String getOrganizationId() { return organizationId; }
    public void setOrganizationId(String v) { organizationId = v; }

    public String getOrganizerUserId() { return organizerUserId; }
    public void setOrganizerUserId(String v) { organizerUserId = v; }

    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer v) { capacity = v; }

    public Set<String> getTags() { return tags; }
    public void setTags(Set<String> v) { tags = v; }

    public EventStatus getStatus() { return status; }
    public void setStatus(EventStatus v) { status = v; }

    public EventVisibility getVisibility() { return visibility; }
    public void setVisibility(EventVisibility v) { visibility = v; }
}
