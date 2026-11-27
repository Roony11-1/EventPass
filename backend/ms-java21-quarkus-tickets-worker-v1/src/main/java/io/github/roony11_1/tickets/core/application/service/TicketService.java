package io.github.roony11_1.tickets.core.application.service;

import java.util.List;
import java.util.stream.IntStream;

import io.github.roony11_1.tickets.core.application.event.OrdenCreadaEvent;
import io.github.roony11_1.tickets.core.domain.exception.TicketInvalidoException;
import io.github.roony11_1.tickets.core.domain.models.Ticket;
import io.github.roony11_1.tickets.core.domain.repository.ITicketRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor 
public class TicketService
{
    private final ITicketRepository ticketRepository;

    public Uni<List<Ticket>> buscarPorOrden(String ordenId)
    {
        return ticketRepository.buscarPorOrden(ordenId);
    }

    public Uni<List<Ticket>> emitirTickets(OrdenCreadaEvent evento)
    {
        validar(evento);

        var operaciones = evento.items().stream()
            .flatMap(item -> IntStream.range(0, item.cantidad())
                .mapToObj(i -> ticketRepository.guardar(
                    Ticket.crear(
                        evento.ordenId(),
                        evento.eventoId(),
                        item.tipoEntradaId()
                    )
                )))
            .toList();

        return Uni.join().all(operaciones).andCollectFailures();
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
