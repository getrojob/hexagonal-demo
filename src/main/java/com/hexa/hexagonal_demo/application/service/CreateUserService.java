package com.hexa.hexagonal_demo.application.service;

import java.util.UUID;

import com.hexa.hexagonal_demo.domain.model.User;
import com.hexa.hexagonal_demo.domain.port.in.CreateUserCommand;
import com.hexa.hexagonal_demo.domain.port.in.CreateUserUseCase;
import com.hexa.hexagonal_demo.domain.port.out.PasswordEncoderPort;
import com.hexa.hexagonal_demo.domain.port.out.UserRepository;

public class CreateUserService implements CreateUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoderPort passwordEncoder;

    public CreateUserService(UserRepository userRepository, PasswordEncoderPort passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User execute(CreateUserCommand command) {
        var user = new User(
                UUID.randomUUID(),
                command.name(),
                command.email(),
                passwordEncoder.encode(command.senha()),
                        command.cpf()
        );
        return userRepository.save(user);
    }

}
