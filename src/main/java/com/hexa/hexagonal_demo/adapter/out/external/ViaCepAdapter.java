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

    public ViaCepAdapter() {
        this.restClient = RestClient.create();
    }

    @Override
    public Optional<Address> fetchAddressByZipCode(String zipCode) {
        try {
            // DTO específico para o JSON do terceiro
            ViaCepDto response = restClient.get()
                    .uri(URL, zipCode)
                    .retrieve()
                    .body(ViaCepDto.class);

            if (response == null || response.cep() == null)
                return Optional.empty();

            // Converte o DTO do terceiro para o seu modelo de Domínio
            return Optional.of(
                    new Address(response.cep(), response.logradouro(), response.complemento(), response.unidade(),
                            response.bairro(),
                            response.localidade(), response.uf(), response.estado(), response.regiao(), response.ibge(),
                            response.gia(), response.ddd(),
                            response.siafi()));

        } catch (Exception e) {
            return Optional.empty();
        }
    }
}

// Record auxiliar para mapear o JSON do terceiro
record ViaCepDto(String cep, String logradouro, String bairro, String localidade, String uf, String estado,
        String regiao, String complemento, String unidade, String ibge, String gia,
        String ddd, String siafi) {
}
