package io.github.roony11_1.tickets.core.application.event;

import java.time.Instant;
import java.util.List;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record OrdenCreadaEvent(
    String ordenId,
    long eventoId,
    long compradorId,
    List<ItemCreado> items,
    Instant createdAt) 
{
    @RegisterForReflection
    public record ItemCreado(long tipoEntradaId, int cantidad)
    {
        
    }
}
