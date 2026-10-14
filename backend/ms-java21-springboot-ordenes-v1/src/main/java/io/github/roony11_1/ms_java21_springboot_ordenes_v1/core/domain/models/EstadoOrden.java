package io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.domain.models;

public enum EstadoOrden 
{
    PENDIENTE_EMISION,
    EMITIDA,
    RECHAZADA;

    public boolean esTerminal() 
    {
        return this == EMITIDA || this == RECHAZADA;
    }

    public boolean puedeTransitarA(EstadoOrden destino) 
    {
        return this == PENDIENTE_EMISION && (destino == EMITIDA || destino == RECHAZADA);
    }
}