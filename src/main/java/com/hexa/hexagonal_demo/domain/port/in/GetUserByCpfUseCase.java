package com.hexa.hexagonal_demo.domain.port.in;

import com.hexa.hexagonal_demo.domain.model.User;

public interface GetUserByCpfUseCase {
    User execute(GetUserByCpfQuery query);
}

