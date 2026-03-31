package com.hexa.hexagonal_demo.adapter.out.persistence;

import java.util.UUID;

import com.hexa.hexagonal_demo.domain.model.User;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class UserEntity {

    @Id
    private UUID id;
    private String name;
    private String email;
    private String cpf;

    public static UserEntity fromDomain(User user) {
        var entity = new UserEntity();
        entity.id = user.getId();
        entity.name = user.getName();
        entity.email = user.getEmail();
        entity.cpf = user.getCpf();
        return entity;
    }

    public User toDomain() {
        return new User(id, name, email, cpf);
    }
}
