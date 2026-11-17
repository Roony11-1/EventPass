package io.github.roony11_1.tickets.core.application.event;

import java.util.List;

import io.github.roony11_1.tickets.core.domain.models.Ticket;

public interface IOrdenCreadaProcesor 
{
    List<Ticket> procesar(OrdenCreadaEvent ordenCreadaEvent);
}
