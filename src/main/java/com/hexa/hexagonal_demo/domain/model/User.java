package com.hexa.hexagonal_demo.domain.model;

import java.util.UUID;

public class User {
    private final UUID id;
    private final String name;
    private final String email;
    private final String senhaCriptografada; // O domínio guarda a senha já segura
    private final String cpf;

    public User(UUID id, String name, String email, String senhaCriptografada, String cpf) {
        validate(name, email, cpf);
        this.id = id;
        this.name = name;
        this.email = email;
        this.senhaCriptografada = senhaCriptografada;
        this.cpf = cpf;
    }

    private void validate(String name, String email, String cpf) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (cpf == null || cpf.isEmpty()) {
            throw new IllegalArgumentException("CPF cannot be null or empty");
        }
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getCpf() {
        return cpf;
    }

    public String getSenhaCriptografada() {
        return senhaCriptografada;
    }
}
