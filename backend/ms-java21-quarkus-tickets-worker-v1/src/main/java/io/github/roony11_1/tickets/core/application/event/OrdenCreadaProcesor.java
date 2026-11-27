package io.github.roony11_1.tickets.core.application.event;

import java.util.List;

import io.github.roony11_1.tickets.core.application.service.TicketService;
import io.github.roony11_1.tickets.core.domain.models.Ticket;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class OrdenCreadaProcesor
{
    private final TicketService ticketService;

    public Uni<List<Ticket>> procesar(OrdenCreadaEvent ordenCreadaEvent) 
    {
        return ticketService.buscarPorOrden(ordenCreadaEvent.ordenId())
            .flatMap(existentes -> 
            {
                if (!existentes.isEmpty())
                    return Uni.createFrom().item(existentes);

                return ticketService.emitirTickets(ordenCreadaEvent);
            });
    }
}
