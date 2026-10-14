package io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.domain.models;

import java.time.Instant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.domain.exception.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table (name = "ordenes")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Orden 
{
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "evento_id", nullable = false)
    private long eventoId;

    @Column(name = "comprador_id", nullable = false)
    private long compradorId;

    @Column(name = "ticket_id")
    private Long ticketId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private EstadoOrden estado;

    @CreationTimestamp
    private Instant createdAt;
    @UpdateTimestamp
    private Instant updatedAt;

    private Orden(long eventoId, long compradorId) 
    {
        validarIds(eventoId, compradorId);

        this.eventoId = eventoId;
        this.compradorId = compradorId;
        this.estado = EstadoOrden.PENDIENTE_EMISION;
        this.ticketId = null;
    }

    public static Orden crear(Long eventoId, Long compradorId) 
    {
        return new Orden(eventoId, compradorId);
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

    private static void validarIds(Long eventoId, Long compradorId) 
    {
        if (eventoId <= 0)
            throw new OrdenInvalidaException("eventoId debe ser > 0");

        if (compradorId <= 0)
            throw new OrdenInvalidaException("compradorId debe ser > 0");
    }
}