package io.github.roony11_1.tickets.core.application.service;

import java.util.List;
import java.util.stream.IntStream;

import io.github.roony11_1.tickets.core.application.event.OrdenCreadaEvent;
import io.github.roony11_1.tickets.core.domain.exception.TicketInvalidoException;
import io.github.roony11_1.tickets.core.domain.exception.TicketNoEncontradoException;
import io.github.roony11_1.tickets.core.domain.models.Ticket;
import io.github.roony11_1.tickets.core.domain.repository.ITicketRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor 
public class TicketService 
{
    private final ITicketRepository ticketRepository;

    public List<Ticket> emitirTickets(OrdenCreadaEvent evento)
    {
        validar(evento);

        return evento.items().stream()
            .flatMap(item -> IntStream.range(0, item.cantidad())
                .mapToObj(i -> ticketRepository.guardar(Ticket.crear(evento.ordenId(), evento.eventoId(), item.tipoEntradaId()))))
            .toList();
    }

    public Ticket buscarPorId(String id)
    {
        return ticketRepository.buscarPorId(id)
            .orElseThrow(() -> new TicketNoEncontradoException(id));
    }

    public List<Ticket> buscarPorOrden(String ordenId)
    {
        return ticketRepository.buscarPorOrden(ordenId);
    }

    private void validar(OrdenCreadaEvent evento)
    {
        if (evento == null)
            throw new TicketInvalidoException("evento es obligatorio");

        if (evento.ordenId() == null || evento.ordenId().isBlank())
            throw new TicketInvalidoException("ordenId es obligatorio");
        
        if (evento.items() == null || evento.items().isEmpty())
            throw new TicketInvalidoException("el evento debe traer al menos un item");
    }
}
