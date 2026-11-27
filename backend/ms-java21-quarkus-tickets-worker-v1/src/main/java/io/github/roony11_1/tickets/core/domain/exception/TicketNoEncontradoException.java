package io.github.roony11_1.tickets.core.domain.exception;

import io.github.roony11_1.error.core.exceptions.AppException;

public class TicketNoEncontradoException extends AppException
{
    public TicketNoEncontradoException(String id)
    {
        super("TIC-404", "Ticket no encontrado: " + id, TicketErrorCategory.NOT_FOUND, "Ticket no encontrado: " + id);
    }

    public TicketNoEncontradoException(String id, Throwable cause)
    {
        super("TIC-404", "Ticket no encontrado: " + id, TicketErrorCategory.NOT_FOUND, "Ticket no encontrado: " + id);
        initCause(cause);
    }
}
