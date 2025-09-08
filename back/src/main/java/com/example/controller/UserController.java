package com.example.controller;

import com.example.model.User;
import com.example.payload.request.UpdateUserRequest;
import com.example.payload.response.UserDto;
import com.example.repository.UserRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;
    
    public UserController(UserRepository repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    private UserDto toDto(User u) {
        return new UserDto(
            u.getId(),
            u.getFirstname(),
            u.getLastname(),
            u.getUsername(),
            u.getEmail(),
            u.getRoles(),
            u.getAssociationId()
        );
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDto> listAll() {
        return repo.findAll().stream()
            .map(this::toDto)
            .toList();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == principal.username")
    public ResponseEntity<UserDto> getById(@PathVariable String id) {
        return repo.findById(id)
            .map(u -> ResponseEntity.ok(toDto(u)))
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("#id == principal.username")
    public ResponseEntity<UserDto> updateUser(@PathVariable String id, @Valid @RequestBody UpdateUserRequest req) {
        return repo.findById(id).map(u -> {
            u.setFirstname(req.getFirstname());
            u.setLastname(req.getLastname());
            if (req.getPassword() != null && !req.getPassword().isBlank()) {
                u.setPassword(passwordEncoder.encode(req.getPassword()));
            }
            User updated = repo.save(u);
            return ResponseEntity.ok(toDto(updated));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        if (!repo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
