package io.github.roony11_1.tickets.infrastructure.persistence.mongo;

import java.util.List;

import io.github.roony11_1.tickets.core.domain.models.Ticket;
import io.github.roony11_1.tickets.core.domain.models.TicketEstado;
import io.github.roony11_1.tickets.core.domain.repository.ITicketRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class TicketMongoAdapter implements ITicketRepository
{
    private final TicketMongoRepository repository;

    @Override
    public Uni<Ticket> guardar(Ticket ticket)
    {
        TicketDocument d = new TicketDocument();

        d.codigo = ticket.getCodigo();
        d.ordenId = ticket.getOrdenId();
        d.eventoId = ticket.getEventoId();
        d.tipoEntradaId = ticket.getTipoEntradaId();
        d.estado = ticket.getEstado().name();
        d.createdAt = ticket.getCreatedAt();

        repository.persist(d);

        return repository.persist(d)
            .map(this::aDominio);
    }

    @Override
    public Uni<List<Ticket>> buscarPorOrden(String ordenId)
    {
        return repository.buscarPorOrden(ordenId)
            .map(tickets -> tickets.stream()
                .map(this::aDominio)
                .toList());
    }

    private Ticket aDominio(TicketDocument d)
    {
        return Ticket.rehidratar(
            d.id.toString(), d.codigo, d.ordenId, d.eventoId,
            d.tipoEntradaId, TicketEstado.valueOf(d.estado), d.createdAt);
    }
}
