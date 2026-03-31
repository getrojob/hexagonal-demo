package com.hexa.hexagonal_demo.adapter.out.external;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.hexa.hexagonal_demo.domain.model.Address;
import com.hexa.hexagonal_demo.domain.port.out.AddressExternalService;

@Component
public class ViaCepAdapter implements AddressExternalService {

    private final RestClient restClient;
    private final String URL = "https://viacep.com.br/ws/{cep}/json/";

    public ViaCepAdapter(RestClient.Builder builder) {
        this.restClient = builder.build();
    }

    @Override
    public Optional<Address> fetchAddressByZipCode(String zipCode) {
        try {
            // DTO específico para o JSON do terceiro
            ViaCepDto response = restClient.get()
                    .uri(URL, zipCode)
                    .retrieve()
                    .body(ViaCepDto.class);
            
            if (response == null || response.cep() == null) return Optional.empty();

            // Converte o DTO do terceiro para o seu modelo de Domínio
            return Optional.of(new Address(response.cep(), response.logradouro(), null, null, null, response.localidade(), null, null, null, null, null, null, null));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}

// Record auxiliar para mapear o JSON do terceiro
record ViaCepDto(String cep, String logradouro, String localidade) {}
