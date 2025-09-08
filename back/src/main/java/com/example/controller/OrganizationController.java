package com.example.controller;

import com.example.payload.request.OrganizationCreateRequest;
import com.example.payload.request.OrganizationUpdateRequest;
import com.example.payload.response.OrganizationDto;
import com.example.service.OrganizationService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/organizations")
public class OrganizationController {
    private final OrganizationService orgService;
    
    public OrganizationController(OrganizationService orgService) {
        this.orgService = orgService;
    }

    @GetMapping
    public List<OrganizationDto> listAll() { return orgService.listAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<OrganizationDto> getById(@PathVariable String id) {
        return ResponseEntity.ok(orgService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZER')")
    public ResponseEntity<OrganizationDto> create(@Valid @RequestBody OrganizationCreateRequest req) {
        return ResponseEntity.status(201).body(orgService.create(req));
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZER')")
    public ResponseEntity<OrganizationDto> update(@PathVariable String id, @Valid @RequestBody OrganizationUpdateRequest req) {
        return ResponseEntity.ok(orgService.update(id, req));
    }

    @DeleteMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZER')")
    public ResponseEntity<OrganizationDto> delete(@PathVariable String id) {
        orgService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
