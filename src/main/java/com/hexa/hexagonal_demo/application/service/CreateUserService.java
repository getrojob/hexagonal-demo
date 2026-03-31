package com.hexa.hexagonal_demo.application.service;

import java.util.UUID;

import com.hexa.hexagonal_demo.domain.model.User;
import com.hexa.hexagonal_demo.domain.port.in.CreateUserCommand;
import com.hexa.hexagonal_demo.domain.port.in.CreateUserUseCase;
import com.hexa.hexagonal_demo.domain.port.out.UserRepository;

public class CreateUserService implements CreateUserUseCase {

    private final UserRepository userRepository;

    public CreateUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User execute(CreateUserCommand command) {
        var user = new User(
                UUID.randomUUID(),
                command.name(),
                command.email(),
                command.cpf()
        );
        return userRepository.save(user);
    }

}
