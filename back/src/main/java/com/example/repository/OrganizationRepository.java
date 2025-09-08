package com.example.repository;

import com.example.model.Organization;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrganizationRepository extends MongoRepository<Organization, String> {
    Optional<Organization> findByName(String name);
    boolean existsByName(String name);

    Optional<Organization> findByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCase(String name);

    void deleteByName(String name);
}
