package com.eventpass.eventos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class TipoEntradaResponse {
    private Long id;
    private String nombre;
    private BigDecimal precio;
}