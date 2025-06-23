package com.example.security;

import com.example.model.User;
import com.example.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.stream.Collectors;

@Service  // ← c'est la clé : expose ce service comme un bean UserDetailsService
public class EmailUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public EmailUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("No user with email: " + email));

        return org.springframework.security.core.userdetails.User
            .withUsername(user.getEmail())
            .password(user.getPassword())
            .authorities(
              user.getRoles()
                  .stream()
                  .map(SimpleGrantedAuthority::new)
                  .collect(Collectors.toList())
            )
            .build();
    }
}
