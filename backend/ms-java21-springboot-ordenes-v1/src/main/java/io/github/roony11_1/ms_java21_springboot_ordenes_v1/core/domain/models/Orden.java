package io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.domain.models;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.domain.exception.OrdenInvalidaException;
import io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.domain.exception.TransicionEstadoNoPermitidaException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Orden
{
    private String id;

    private long eventoId;

    private long compradorId;

    private List<ItemOrden> items;

    private Long ticketId;

    private EstadoOrden estado;

    private Instant createdAt;

    private Instant updatedAt;

    private Long version;

    private Orden(long eventoId, long compradorId, List<ItemOrden> items)
    {
        validarIds(eventoId, compradorId);

        if (items == null || items.isEmpty())
            throw new OrdenInvalidaException("la orden debe tener al menos un item");

        if (items.stream().anyMatch(item -> item == null))
            throw new OrdenInvalidaException("los items no pueden ser nulos");

        this.eventoId = eventoId;
        this.compradorId = compradorId;
        this.items = new ArrayList<>(items);
        this.estado = EstadoOrden.PENDIENTE_EMISION;
        this.ticketId = null;
    }

    public static Orden crear(Long eventoId, Long compradorId, List<ItemOrden> items)
    {
        if (eventoId == null)
            throw new OrdenInvalidaException("eventoId es obligatorio");
        if (compradorId == null)
            throw new OrdenInvalidaException("compradorId es obligatorio");
        if (items == null)
            throw new OrdenInvalidaException("items es obligatorio");
        return new Orden(eventoId, compradorId, items);
    }

    public static Orden rehidratar(String id, long eventoId,
        long compradorId, List<ItemOrden> items, Long ticketId,
        EstadoOrden estado, Instant createdAt,
        Instant updatedAt, Long version)
    {
        Orden o = new Orden();

        o.id = id;
        o.eventoId = eventoId;
        o.compradorId = compradorId;
        o.items = items == null ? new ArrayList<>() : new ArrayList<>(items);
        o.ticketId = ticketId;
        o.estado = estado;
        o.createdAt = createdAt;
        o.updatedAt = updatedAt;
        o.version = version;

        return o;
    }

    public List<ItemOrden> getItems()
    {
        return Collections.unmodifiableList(items);
    }

    public int getCantidadTotal()
    {
        return items.stream().mapToInt(ItemOrden::getCantidad).sum();
    }

    public void marcarEmitida(long ticketId)
    {
        if (!estado.puedeTransitarA(EstadoOrden.EMITIDA))
            throw new TransicionEstadoNoPermitidaException(estado, EstadoOrden.EMITIDA);

        if (ticketId <= 0)
            throw new OrdenInvalidaException("ticketId debe ser > 0");

        this.ticketId = ticketId;
        this.estado = EstadoOrden.EMITIDA;
    }

    public void marcarRechazada()
    {
        if (!estado.puedeTransitarA(EstadoOrden.RECHAZADA))
            throw new TransicionEstadoNoPermitidaException(estado, EstadoOrden.RECHAZADA);

        this.estado = EstadoOrden.RECHAZADA;
    }

    private static void validarIds(long eventoId, long compradorId)
    {
        if (eventoId <= 0)
            throw new OrdenInvalidaException("eventoId debe ser > 0");

        if (compradorId <= 0)
            throw new OrdenInvalidaException("compradorId debe ser > 0");
    }
}
