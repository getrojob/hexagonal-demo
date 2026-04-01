package com.hexa.hexagonal_demo.adapter.out.external;

import java.util.Optional;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.hexa.hexagonal_demo.domain.model.Feriado;
import com.hexa.hexagonal_demo.domain.port.out.FeriadoService;

@Component
public class FeriadosAdapter implements FeriadoService {

    private final RestClient restClient;
    private final String URL = "https://brasilapi.com.br/api/feriados/v1/{ano}";

    public FeriadosAdapter() {
        this.restClient = RestClient.create();
    }

    @Override
    public Optional<List<Feriado>> fetchFeriadoByYear(String year) {
        try {
            // Como a API retorna uma LISTA de feriados, usamos ParameterizedTypeReference
            List<FeriadoDto> response = restClient.get()
                    .uri(URL, year)
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<FeriadoDto>>() {
                    });

            if (response == null || response.isEmpty()) {
                return Optional.empty();
            }

            // Convertendo a lista de DTOs (Infra) para a lista de Feriado (Domínio)
            List<Feriado> feriadosDomain = response.stream()
                    .map(dto -> new Feriado(dto.date(), dto.name(), dto.type()))
                    .toList();

            return Optional.of(feriadosDomain);

        } catch (Exception e) {
            return Optional.empty();
        }
    }
}

// Record auxiliar para mapear o JSON do terceiro
record FeriadoDto(String date, String name, String type) {
}
