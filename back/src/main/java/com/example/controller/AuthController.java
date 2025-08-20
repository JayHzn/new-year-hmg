package com.example.controller;

import com.example.model.User;
import com.example.model.Role;
import com.example.repository.UserRepository;
import com.example.payload.request.RegisterRequest;
import com.example.payload.request.LoginRequest;
import com.example.payload.response.JwtResponse;
import com.example.payload.response.MessageResponse;
import com.example.security.JwtUtil;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authManager, JwtUtil jwtUtil,
        UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest req) {
        Authentication authentication = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                req.getUsername(), req.getPassword()
            )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        
        String token = jwtUtil.generateToken(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
            .map(g -> g.getAuthority().replace("ROLE_", ""))
            .collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(
            token,
            userDetails.getUsername(),
            roles
        ));
    }
    
    // REGISTER
    @PostMapping("/register")
    public ResponseEntity<MessageResponse> register(@Valid @RequestBody
	    RegisterRequest req) {

        if (userRepository.existsByUsername(req.getUsername())) {
        return ResponseEntity
            .badRequest()
            .body(new MessageResponse("Username déjà utilisé"));
    }
        if (userRepository.existsByEmail(req.getEmail())) {
            return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Email déjà utilisé"));
        }

        User newUser = new User();
        newUser.setFirstname(req.getFirstname());
        newUser.setLastname(req.getLastname());
        newUser.setUsername(req.getUsername());
        newUser.setEmail(req.getEmail());
        newUser.setPassword(passwordEncoder.encode(req.getPassword()));
        newUser.addRole(Role.USER);
        userRepository.save(newUser);

        if (req.getAssociationId() != null && !req.getAssociationId().isBlank()) {
            newUser.addRole(Role.ORGANIZER);
            newUser.setAssociationId(req.getAssociationId());
        }

        userRepository.save(newUser);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(new MessageResponse("Inscription réussie"));
    }
}
