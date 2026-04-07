package com.hexa.hexagonal_demo.config.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hexa.hexagonal_demo.adapter.out.persistence.SpringDataUserRepository;

@Service
public class AuthenticationService implements UserDetailsService {

    private final SpringDataUserRepository repository;

    public AuthenticationService(SpringDataUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByCpf(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with CPF: " + username));
    }
}
