package com.hexa.hexagonal_demo.domain.port.in;

import com.hexa.hexagonal_demo.domain.model.User;

public interface CreateUserUseCase {
    User execute(CreateUserCommand command);
}
