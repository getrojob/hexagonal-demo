package com.hexa.hexagonal_demo.domain.port.in;

import com.hexa.hexagonal_demo.domain.model.Address;

public interface GetAddressUseCase {
    Address execute(GetAddressQuery query);
}
