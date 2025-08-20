package com.example.service;

import com.example.model.User;
import com.example.exception.RessourceNotFoundException;
import com.example.model.Role;
import com.example.repository.UserRepository;
import com.example.payload.request.RegisterRequest;
import com.example.payload.request.LoginRequest;
import com.example.payload.request.UpdateUserRequest;
import com.example.payload.response.MessageResponse;
import com.example.payload.response.UserDto;
import com.example.payload.response.JwtResponse;
import com.example.security.JwtUtil;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.security.GlobalExceptionHandler;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder, AuthenticationManager authManager, JwtUtil jwtUtil) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
    }

    public MessageResponse register(@Valid RegisterRequest req) {
        if (userRepo.existsByUsername(req.getUsername())) {
            throw new IllegalArgumentException("Username already in use");
        }

        if (userRepo.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        User u = new User();
        u.setFirstname(req.getFirstname());
        u.setLastname(req.getLastname());
        u.setUsername(req.getUsername());
        u.setEmail(req.getEmail());
        u.setPassword(req.getPassword());
        u.addRole(Role.USER);
        if (req.getAssociationId() != null && !req.getAssociationId().isBlank()) {
            u.addRole(Role.ORGANIZER);
            u.setAssociationId(req.getAssociationId());
        }
        userRepo.save(u);
        return new MessageResponse("Inscription Réussie");
    }

    public JwtResponse authenticate(@Valid LoginRequest req) {
        Authentication auth = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()
            )
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        String token = jwtUtil.generateToken(auth);
        List<String> roles = auth.getAuthorities().stream()
            .map(a -> a.getAuthority().replace("ROLE_", ""))
            .collect(Collectors.toList());
        return new JwtResponse(token, auth.getName(), roles);
    }

    public List<UserDto> getAll() {
        return userRepo.findAll().stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    public UserDto getById(String id) {
        User u = userRepo.findById(id)
            .orElseThrow(() -> new RessourceNotFoundException("User not found: "+id));
        return toDto(u);
    }

    public UserDto update(String id, @Valid UpdateUserRequest req) {
        User u = userRepo.findById(id)
            .orElseThrow(() -> new RessourceNotFoundException("User not found: "+id));
        u.setFirstname(req.getFirstname());
        u.setLastname(req.getLastname());
        if (req.getPassword() != null && !req.getPassword().isBlank()) {
            u.setPassword(passwordEncoder.encode(req.getPassword()));
        }
        return toDto(userRepo.save(u));
    }

    private UserDto toDto(User u) {
        return new UserDto(
            u.getId(),
            u.getFirstname(),
            u.getLastname(),
            u.getUsername(),
            u.getEmail(),
            u.getRoles().stream().map(Enum::name).collect(Collectors.toSet()),
            u.getAssociationId()
        );
    }
}
