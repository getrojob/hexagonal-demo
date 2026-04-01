package com.hexa.hexagonal_demo.domain.port.out;

import java.util.List;
import java.util.Optional;

import com.hexa.hexagonal_demo.domain.model.Feriado;

public interface FeriadoService {
    Optional<List<Feriado>> fetchFeriadoByYear(String year);
}
