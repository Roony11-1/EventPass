package io.github.roony11_1.tickets.core.application.service;

import java.util.List;

import io.github.roony11_1.tickets.core.domain.exception.TicketNoEncontradoException;
import io.github.roony11_1.tickets.core.domain.exception.TicketYaUtilizadoException;
import io.github.roony11_1.tickets.core.domain.models.Ticket;
import io.github.roony11_1.tickets.core.domain.models.TicketEstado;
import io.github.roony11_1.tickets.core.domain.repository.ITicketRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor 
public class TicketService 
{
    private final ITicketRepository ticketRepository;

    public Ticket buscarPorId(String id)
    {
        return ticketRepository.buscarPorId(id)
            .orElseThrow(() -> new TicketNoEncontradoException(id));
    }

    public List<Ticket> buscarPorOrden(String ordenId)
    {
        return ticketRepository.buscarPorOrden(ordenId);
    }

    public Ticket validarTicket(String codigo)
    {
        Ticket ticket = ticketRepository.buscarPorCodigo(codigo)
            .orElseThrow(() -> new TicketNoEncontradoException(codigo));

        if (ticket.getEstado() != TicketEstado.EMITIDO)
            throw new TicketYaUtilizadoException(codigo);

        ticket.validar();

        return ticketRepository.guardar(ticket);
    }
}
