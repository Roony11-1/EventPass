package io.github.roony11_1.tickets.core.domain.exception;

import io.github.roony11_1.error.core.exceptions.AppException;

public class TicketInvalidoException extends AppException
{
    public TicketInvalidoException(String detalle)
    {
        super("TIC-400", "Ticket inválido: " + detalle, TicketErrorCategory.INVALID, "Ticket inválido: " + detalle);
    }

    public TicketInvalidoException(String detalle, Throwable cause)
    {
        super("TIC-400", "Ticket inválido: " + detalle, TicketErrorCategory.INVALID, "Ticket inválido: " + detalle);
        initCause(cause);
    }
}
