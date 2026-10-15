package io.github.roony11_1.ms_java21_springboot_ordenes_v1.infrastructure.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventoReservaResponse
{
    private boolean reservado;
    private int aforoDisponible;
}
