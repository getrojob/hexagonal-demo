package com.hexa.hexagonal_demo.application.service;

import java.util.UUID;

import com.hexa.hexagonal_demo.domain.model.User;
import com.hexa.hexagonal_demo.domain.port.in.GetUserQuery;
import com.hexa.hexagonal_demo.domain.port.in.GetUserUseCase;
import com.hexa.hexagonal_demo.domain.port.out.UserRepository;

public class GetUserService implements GetUserUseCase {

    private final UserRepository userRepository;

    public GetUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User execute(GetUserQuery query) {
        return userRepository.findById(query.id())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }
}
