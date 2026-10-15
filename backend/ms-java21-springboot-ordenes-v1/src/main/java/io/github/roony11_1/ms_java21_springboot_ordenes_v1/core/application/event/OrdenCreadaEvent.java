package io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.application.event;

import java.time.Instant;
import java.util.List;

public record OrdenCreadaEvent(
    String ordenId,
    long eventoId,
    long compradorId,
    List<ItemCreado> items,
    Instant createdAt)
{
    public record ItemCreado(long tipoEntradaId, int cantidad) {}
}
