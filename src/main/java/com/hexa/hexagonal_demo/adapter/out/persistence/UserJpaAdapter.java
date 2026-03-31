package com.hexa.hexagonal_demo.adapter.out.persistence;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.hexa.hexagonal_demo.domain.model.User;
import com.hexa.hexagonal_demo.domain.port.out.UserRepository;

@Repository
public class UserJpaAdapter implements UserRepository {

    private final SpringDataUserRepository repository;

    public UserJpaAdapter(SpringDataUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User save(User user) {
        return repository.save(UserEntity.fromDomain(user)).toDomain();
    }

    @Override
    public Optional<User> findById(UUID id) {
        return repository.findById(id).map(UserEntity::toDomain);
    }

    @Override
    public Optional<User> findByCpf(String cpf) {
        return repository.findByCpf(cpf).map(UserEntity::toDomain);
    }
}
