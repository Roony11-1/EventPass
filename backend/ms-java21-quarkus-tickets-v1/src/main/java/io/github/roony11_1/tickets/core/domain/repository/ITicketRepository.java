package io.github.roony11_1.tickets.core.domain.repository;

import java.util.List;
import java.util.Optional;

import io.github.roony11_1.tickets.core.domain.models.Ticket;

public interface ITicketRepository
{
    Ticket guardar(Ticket ticket);

    Optional<Ticket> buscarPorId(String id);

    Optional<Ticket> buscarPorCodigo(String codigo);

    List<Ticket> buscarPorOrden(String ordenId);
}
