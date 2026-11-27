package io.github.roony11_1.tickets.core.domain.repository;

import java.util.List;

import io.github.roony11_1.tickets.core.domain.models.Ticket;

public interface ITicketRepository
{
    Ticket guardar(Ticket ticket);

    List<Ticket> buscarPorOrden(String ordenId);
}
