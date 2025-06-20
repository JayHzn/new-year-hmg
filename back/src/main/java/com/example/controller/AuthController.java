package com.example.controller;

import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authManager, JwtUtil jwtUtil,
        UserRepository userRepository, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody AuthRequest request) {
        Authentication authentication = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(), request.getPassword()
            )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        
        String token = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }
    
    // REGISTER
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody
	    AuthRequest req) {
        if (userRepository.existsByUsername(req.getUsername())) {
        return ResponseEntity
            .badRequest()
            .body(Map.of("error", "Username already exists"));
    }
        if (userRepository.existsByEmail(req.getEmail())) {
            return ResponseEntity
                .badRequest()
                .body(Collections.singletonMap("error", "Email already exists"));
        }

        User newUser = new User();
        newUser.setUsername(req.getUsername());
        newUser.setEmail(req.getEmail());
        newUser.setPassword(passwordEncoder.encode(req.getPassword()));
        newUser.setRoles(Set.of("USER"));
        userRepository.save(newUser);

        UserDetails userDetails = userDetailsService.loadUserByUsername(newUser.getEmail());

        String token = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }
}
