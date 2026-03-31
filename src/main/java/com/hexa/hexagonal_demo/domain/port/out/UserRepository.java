package com.hexa.hexagonal_demo.domain.port.out;

import java.util.Optional;
import java.util.UUID;

import com.hexa.hexagonal_demo.domain.model.User;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(UUID id);
    Optional<User> findByCpf(String cpf);   
}
