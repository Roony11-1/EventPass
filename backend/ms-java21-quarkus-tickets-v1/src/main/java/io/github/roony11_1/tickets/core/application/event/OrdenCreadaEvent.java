package io.github.roony11_1.tickets.core.application.event;

import java.time.Instant;
import java.util.List;

public record OrdenCreadaEvent(
    String ordenId,
    long eventoId,
    long compradorId,
    List<ItemCreado> items,
    Instant createdAt) 
{
    public record ItemCreado(long tipoEntradaId, int cantidad)
    {
        
    }
}
