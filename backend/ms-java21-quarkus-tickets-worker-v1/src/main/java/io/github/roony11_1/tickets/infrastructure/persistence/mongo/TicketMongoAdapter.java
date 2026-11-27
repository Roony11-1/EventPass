package io.github.roony11_1.tickets.infrastructure.persistence.mongo;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;

import io.github.roony11_1.tickets.core.domain.models.Ticket;
import io.github.roony11_1.tickets.core.domain.models.TicketEstado;
import io.github.roony11_1.tickets.core.domain.repository.ITicketRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

@ApplicationScoped 
@RequiredArgsConstructor 
public class TicketMongoAdapter implements ITicketRepository
{
    private final TicketMongoRepository repository;

    @Override
    public void guardar(Ticket ticket)
    {
        TicketDocument d = new TicketDocument();

        d.codigo = ticket.getCodigo();
        d.ordenId = ticket.getOrdenId();
        d.eventoId = ticket.getEventoId();
        d.tipoEntradaId = ticket.getTipoEntradaId();
        d.estado = ticket.getEstado().name();
        d.createdAt = ticket.getCreatedAt();

        repository.persist(d);
    }
}
