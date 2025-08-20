package com.example.security;

import com.example.model.User;
import com.example.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    private final UserRepository userRepo;

    public UserDetailsServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String login) {
        User user = userRepo.findByUsername(login)
            .or(() -> userRepo.findByEmail(login))
            .orElseThrow(() ->
                new UsernameNotFoundException("Utilisateur non trouvé : " + login)
            );
        return user;
    }
}
