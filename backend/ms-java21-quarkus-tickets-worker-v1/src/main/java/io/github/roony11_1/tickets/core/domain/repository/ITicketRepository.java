package io.github.roony11_1.tickets.core.domain.repository;

import java.util.List;

import io.github.roony11_1.tickets.core.domain.models.Ticket;
import io.smallrye.mutiny.Uni;

public interface ITicketRepository
{
    Uni<Ticket> guardar(Ticket ticket);

    Uni<List<Ticket>> buscarPorOrden(String ordenId);
}
