package com.eventpass.eventos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReservaResponse {
    private boolean reservado;
    private int aforoDisponible;
}