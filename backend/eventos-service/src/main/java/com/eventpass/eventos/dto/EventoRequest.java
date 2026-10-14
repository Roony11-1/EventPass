package com.eventpass.eventos.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class EventoRequest {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    private String descripcion;

    @NotNull(message = "La fecha es obligatoria")
    @Future(message = "La fecha debe ser futura")
    private LocalDateTime fecha;

    @NotBlank(message = "El lugar es obligatorio")
    private String lugar;

    @NotNull(message = "El aforo total es obligatorio")
    @Positive(message = "El aforo total debe ser mayor a 0")
    private Integer aforoTotal;

    @Valid
    private List<TipoEntradaRequest> tiposEntrada;
}