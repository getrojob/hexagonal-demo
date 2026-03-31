package com.hexa.hexagonal_demo.domain.port.out;

import java.util.Optional;

import com.hexa.hexagonal_demo.domain.model.Address;

public interface AddressExternalService {
    Optional<Address> fetchAddressByZipCode(String zipCode);
}
