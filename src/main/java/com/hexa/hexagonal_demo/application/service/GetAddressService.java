package com.hexa.hexagonal_demo.application.service;

import com.hexa.hexagonal_demo.domain.model.Address;
import com.hexa.hexagonal_demo.domain.port.in.GetAddressQuery;
import com.hexa.hexagonal_demo.domain.port.in.GetAddressUseCase;
import com.hexa.hexagonal_demo.domain.port.out.AddressExternalService;

public class GetAddressService implements GetAddressUseCase {

    private final AddressExternalService addressExternalService;

    public GetAddressService(AddressExternalService addressExternalService) {
        this.addressExternalService = addressExternalService;
    }

    @Override
    public Address execute(GetAddressQuery query) {
        // Aqui você usa o método do seu Adapter
        return addressExternalService.fetchAddressByZipCode(query.zipCode())
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado para o CEP: " + query.zipCode()));
    }
}
