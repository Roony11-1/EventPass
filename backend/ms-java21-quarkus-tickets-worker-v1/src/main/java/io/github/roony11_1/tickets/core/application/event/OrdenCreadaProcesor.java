package io.github.roony11_1.tickets.core.application.event;

import java.util.List;

import io.github.roony11_1.tickets.core.application.service.TicketService;
import io.github.roony11_1.tickets.core.domain.models.Ticket;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class OrdenCreadaProcesor implements IOrdenCreadaProcesor
{
    private final TicketService ticketService;

    @Override
    public List<Ticket> procesar(OrdenCreadaEvent ordenCreadaEvent) 
    {
        var existentes = ticketService.buscarPorOrden(ordenCreadaEvent.ordenId());

        // Idempotencia básica: si la orden ya tiene tickets, no se vuelven a emitir.
        if (!existentes.isEmpty())
            return existentes;

        return ticketService.emitirTickets(ordenCreadaEvent);
    }
}
