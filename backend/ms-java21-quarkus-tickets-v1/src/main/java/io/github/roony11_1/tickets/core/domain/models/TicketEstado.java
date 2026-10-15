package io.github.roony11_1.tickets.core.domain.models;

public enum TicketEstado
{
    EMITIDO,
    VALIDADO,
    ANULADO;

    public boolean esTerminal()
    {
        return this == VALIDADO || this == ANULADO;
    }

    public boolean puedeTransitarA(TicketEstado destino)
    {
        return this == EMITIDO && (destino == VALIDADO || destino == ANULADO);
    }
}
