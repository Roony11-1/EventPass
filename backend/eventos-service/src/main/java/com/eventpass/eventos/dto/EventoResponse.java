package com.eventpass.eventos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class EventoResponse {
    private Long id;
    private String nombre;
    private String descripcion;
    private LocalDateTime fecha;
    private String lugar;
    private Integer aforoTotal;
    private Integer aforoDisponible;
    private List<TipoEntradaResponse> tiposEntrada;
}