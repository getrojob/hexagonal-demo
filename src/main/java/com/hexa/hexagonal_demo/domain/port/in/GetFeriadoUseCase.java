package com.hexa.hexagonal_demo.domain.port.in;

import java.util.List;
import java.util.Optional;

import com.hexa.hexagonal_demo.domain.model.Feriado;

public interface GetFeriadoUseCase {
    Optional<List<Feriado>> execute(GetFeriadoQuery query);
}
