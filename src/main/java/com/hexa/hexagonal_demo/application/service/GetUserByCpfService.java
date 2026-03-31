package com.hexa.hexagonal_demo.application.service;

import com.hexa.hexagonal_demo.domain.model.User;
import com.hexa.hexagonal_demo.domain.port.in.GetUserByCpfQuery;
import com.hexa.hexagonal_demo.domain.port.in.GetUserByCpfUseCase;
import com.hexa.hexagonal_demo.domain.port.out.UserRepository;

public class GetUserByCpfService implements GetUserByCpfUseCase {

    private final UserRepository userRepository;

    public GetUserByCpfService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User execute(GetUserByCpfQuery query) {
        return userRepository.findByCpf(query.cpf())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }
}
