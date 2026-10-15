package io.github.roony11_1.tickets.core.domain.models;

import java.time.Instant;
import java.util.UUID;

import io.github.roony11_1.tickets.core.domain.exception.TicketInvalidoException;
import io.github.roony11_1.tickets.core.domain.exception.TransicionEstadoNoPermitidaException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Ticket
{
    private String id;

    private String codigo;

    private String ordenId;

    private long eventoId;

    private long tipoEntradaId;

    private TicketEstado estado;

    private Instant createdAt;

    private Ticket(String ordenId, long eventoId, long tipoEntradaId)
    {
        if (ordenId == null || ordenId.isBlank())
            throw new TicketInvalidoException("ordenId es obligatorio");
        if (eventoId <= 0)
            throw new TicketInvalidoException("eventoId debe ser > 0");
        if (tipoEntradaId <= 0)
            throw new TicketInvalidoException("tipoEntradaId debe ser > 0");

        this.codigo = UUID.randomUUID().toString();
        this.ordenId = ordenId;
        this.eventoId = eventoId;
        this.tipoEntradaId = tipoEntradaId;
        this.estado = TicketEstado.EMITIDO;
        this.createdAt = Instant.now();
    }

    public static Ticket crear(String ordenId, long eventoId, long tipoEntradaId)
    {
        return new Ticket(ordenId, eventoId, tipoEntradaId);
    }

    public static Ticket rehidratar(String id, String codigo, String ordenId, long eventoId,
        long tipoEntradaId, TicketEstado estado, Instant createdAt)
    {
        Ticket t = new Ticket();

        t.id = id;
        t.codigo = codigo;
        t.ordenId = ordenId;
        t.eventoId = eventoId;
        t.tipoEntradaId = tipoEntradaId;
        t.estado = estado;
        t.createdAt = createdAt;

        return t;
    }

    public void anular()
    {
        if (!estado.puedeTransitarA(TicketEstado.ANULADO))
            throw new TransicionEstadoNoPermitidaException(estado, TicketEstado.ANULADO);

        this.estado = TicketEstado.ANULADO;
    }

    public void validar()
    {
        if (!estado.puedeTransitarA(TicketEstado.VALIDADO))
            throw new TransicionEstadoNoPermitidaException(estado, TicketEstado.VALIDADO);

        this.estado = TicketEstado.VALIDADO;
    }
}
