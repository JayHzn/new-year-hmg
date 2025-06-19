package com.example.controller;

import com.example.model.User;
import com.example.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {
    private final UserRepository repo;
    
    public UserController(UserRepository repo) {
        this.repo = repo;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<User> create(@Valid @RequestBody User user) {
        User saved = repo.save(user);
        return ResponseEntity.ok(saved);
    }

    // READ ALL
    @GetMapping
    public List<User> findAll() {
        return repo.findAll();
    }

    // READ ONE
    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable String id) {
        Optional<User> opt = repo.findById(id);
        return opt.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<User> update(
            @PathVariable String id,
            @Valid @RequestBody User newData) {
        return repo.findById(id)
                .map(user -> {
                    user.setUsername(newData.getUsername());
                    user.setEmail(newData.getEmail());
                    user.setPassword(newData.getPassword());
                    return ResponseEntity.ok(repo.save(user));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        if (!repo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
