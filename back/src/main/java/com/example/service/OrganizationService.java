package com.example.service;

import com.example.model.Organization;
import com.example.exception.RessourceNotFoundException;
import com.example.payload.request.OrganizationCreateRequest;
import com.example.payload.request.OrganizationUpdateRequest;
import com.example.payload.response.OrganizationDto;
import com.example.repository.OrganizationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;

@Service
@Transactional
public class OrganizationService {
    private final OrganizationRepository orgRepo;

    public OrganizationService(OrganizationRepository orgRepo) {
        this.orgRepo = orgRepo;
    }

    public OrganizationDto create(@Valid OrganizationCreateRequest req) {
        if (orgRepo.existsByNameIgnoreCase(req.getName())) {
            throw new IllegalArgumentException("Organization already exists : " + req.getName());
        }
        Organization o = new Organization();
        o.setName(req.getName());
        o.setDescription(req.getDescription());
        o.setAddress(req.getAddress());
        o.setLogoUrl(req.getLogoUrl());
        o.setWebsite(req.getWebsite());
        o.setOwnerUserId(req.getOwnerUserId());
        return toDto(orgRepo.save(o));
    }

    public List<OrganizationDto> listAll() {
        return orgRepo.findAll().stream().map(this::toDto).toList();
    }

    public OrganizationDto getById(String id) {
        return toDto(orgRepo.findById(id)
            .orElseThrow(() -> new RessourceNotFoundException("Organization not found : " + id)));
    }

    public OrganizationDto update(String id, @Valid OrganizationUpdateRequest req) {
        Organization current = orgRepo.findById(id)
            .orElseThrow(() -> new RessourceNotFoundException("Organization not found : " + id));
        
        if (req.getName() != null && !req.getName().equalsIgnoreCase(current.getName())) {
            if (orgRepo.existsByNameIgnoreCase(req.getName())) {
                throw new IllegalArgumentException("Organization already exists : " + req.getName());
            }
            current.setName(req.getName());
        }

        if (req.getDescription() != null) current.setDescription(req.getDescription());
        if (req.getAddress() != null) current.setAddress(req.getAddress());
        if (req.getLogoUrl() != null) current.setLogoUrl(req.getLogoUrl());
        if (req.getWebsite() != null) current.setWebsite(req.getWebsite());
        if (req.getOwnerUserId() != null) current.setOwnerUserId(req.getOwnerUserId());

        return toDto(orgRepo.save(current));
    }

    public void delete(String id) {
        if (!orgRepo.existsById(id)) {
            throw new RessourceNotFoundException("Organization not found : "+id);
        }
        orgRepo.deleteById(id);
    }

    private OrganizationDto toDto(Organization o) {
        return new OrganizationDto(
            o.getId(),
            o.getName(),
            o.getDescription(),
            o.getAddress(),
            o.getLogoUrl(),
            o.getWebsite(),
            o.getOwnerUserId(),
            o.getCreatedAt(),
            o.getUpdatedAt()
        );
    }
}
