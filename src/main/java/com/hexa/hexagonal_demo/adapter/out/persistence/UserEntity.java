package com.hexa.hexagonal_demo.adapter.out.persistence;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.hexa.hexagonal_demo.domain.model.User;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class UserEntity implements UserDetails {

    @Id
    private UUID id;
    private String name;
    private String email;
    private String senhaCriptografada;
    private String cpf;

    public static UserEntity fromDomain(User user) {
        var entity = new UserEntity();
        entity.id = user.getId();
        entity.name = user.getName();
        entity.senhaCriptografada = user.getSenhaCriptografada();
        entity.email = user.getEmail();
        entity.cpf = user.getCpf();
        return entity;
    }

    public User toDomain() {
        return new User(id, name, email, senhaCriptografada, cpf);
    }

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return senhaCriptografada;
    }

    @Override
    public String getUsername() {
        return cpf;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
