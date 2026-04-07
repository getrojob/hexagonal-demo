package com.hexa.hexagonal_demo.domain.port.in;

public record CreateUserCommand(String name, String email, String senha, String cpf) {
}
