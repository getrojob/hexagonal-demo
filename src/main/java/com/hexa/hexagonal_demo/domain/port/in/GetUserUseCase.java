package com.hexa.hexagonal_demo.domain.port.in;

import java.util.UUID;

import com.hexa.hexagonal_demo.domain.model.User;

public interface GetUserUseCase {
    User execute(GetUserQuery query);
}
