package io.github.roony11_1.tickets.core.domain.models;

public enum TicketEstado
{
    EMITIDO,
    ANULADO;

    public boolean esTerminal()
    {
        return this == EMITIDO || this == ANULADO;
    }

    public boolean puedeTransitarA(TicketEstado destino)
    {
        return this == EMITIDO && destino == ANULADO;
    }
}
