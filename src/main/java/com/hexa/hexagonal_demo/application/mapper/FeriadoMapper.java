package com.hexa.hexagonal_demo.application.mapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.hexa.hexagonal_demo.adapter.in.web.FeriadoResponse;
import com.hexa.hexagonal_demo.domain.model.Feriado;

public class FeriadoMapper {
    public static List<FeriadoResponse> toResponse(Optional<List<Feriado>> feriado) {
        if (feriado == null)
            return null;

        return feriado
                .map(lista -> lista.stream()
                        .map(f -> new FeriadoResponse(f.date(), f.name(), f.type()))
                        .toList())
                .orElse(Collections.emptyList());
    }
}
