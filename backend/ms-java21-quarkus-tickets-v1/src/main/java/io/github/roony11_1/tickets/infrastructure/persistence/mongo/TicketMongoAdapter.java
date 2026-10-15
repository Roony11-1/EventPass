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
    public Ticket guardar(Ticket ticket)
    {
        TicketDocument d = new TicketDocument();

        d.codigo = ticket.getCodigo();
        d.ordenId = ticket.getOrdenId();
        d.eventoId = ticket.getEventoId();
        d.tipoEntradaId = ticket.getTipoEntradaId();
        d.estado = ticket.getEstado().name();
        d.createdAt = ticket.getCreatedAt();
        repository.persist(d);
        
        return aDominio(d);
    }

    @Override
    public Optional<Ticket> buscarPorId(String id)
    {
        final ObjectId objectId;
        
        try
        {
            objectId = new ObjectId(id);
        }
        catch (IllegalArgumentException e)
        {
            return Optional.empty();
        }

        return Optional.ofNullable(repository.findById(objectId)).map(this::aDominio);
    }

    @Override
    public List<Ticket> buscarPorOrden(String ordenId)
    {
        return repository.buscarPorOrden(ordenId).stream().map(this::aDominio).toList();
    }

    @Override
    public Optional<Ticket> buscarPorCodigo(String codigo)
    {
        return repository.buscarPorCodigo(codigo).map(this::aDominio);
    }

    private Ticket aDominio(TicketDocument d)
    {
        return Ticket.rehidratar(
            d.id.toString(), d.codigo, d.ordenId, d.eventoId,
            d.tipoEntradaId, TicketEstado.valueOf(d.estado), d.createdAt);
    }
}
