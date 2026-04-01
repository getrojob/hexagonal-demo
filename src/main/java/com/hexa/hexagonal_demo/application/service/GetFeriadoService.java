package com.hexa.hexagonal_demo.application.service;

import java.util.List;
import java.util.Optional;

import com.hexa.hexagonal_demo.domain.model.Feriado;
import com.hexa.hexagonal_demo.domain.port.in.GetFeriadoQuery;
import com.hexa.hexagonal_demo.domain.port.in.GetFeriadoUseCase;
import com.hexa.hexagonal_demo.domain.port.out.FeriadoService;

public class GetFeriadoService  implements GetFeriadoUseCase {

    private final FeriadoService feriadoService;

    public GetFeriadoService(FeriadoService feriadoService) {
        this.feriadoService = feriadoService;
    }

    @Override
    public Optional<List<Feriado>> execute(GetFeriadoQuery query) {
        return feriadoService.fetchFeriadoByYear(query.ano());
    }

}
