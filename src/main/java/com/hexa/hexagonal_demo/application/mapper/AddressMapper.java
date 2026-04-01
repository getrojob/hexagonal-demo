package com.hexa.hexagonal_demo.application.mapper;

import com.hexa.hexagonal_demo.adapter.in.web.AddressResponse;
import com.hexa.hexagonal_demo.domain.model.Address;

public class AddressMapper {
    public static AddressResponse toResponse(Address address) {
        if (address == null) return null;
        
        return new AddressResponse(
            address.getCep(),
            address.getLogradouro(),
            address.getComplemento(),
            address.getUnidade(),
            address.getBairro(),
            address.getLocalidade(),
            address.getUf(),
            address.getEstado(),
            address.getRegiao(),
            address.getIbge(),
            address.getGia(),
            address.getDdd(),
            address.getSiafi()
        );
    }
}
