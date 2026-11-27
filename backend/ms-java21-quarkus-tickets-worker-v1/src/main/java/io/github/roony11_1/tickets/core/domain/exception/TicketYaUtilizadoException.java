package io.github.roony11_1.tickets.core.domain.exception;

import io.github.roony11_1.error.core.exceptions.AppException;

public class TicketYaUtilizadoException extends AppException
{
    public TicketYaUtilizadoException(String codigo)
    {
        super("TIC-409", "Ticket ya utilizado: " + codigo, TicketErrorCategory.CONFLICT, "Ticket ya utilizado: " + codigo);
    }

    public TicketYaUtilizadoException(String codigo, Throwable cause)
    {
        super("TIC-409", "Ticket ya utilizado: " + codigo, TicketErrorCategory.CONFLICT, "Ticket ya utilizado: " + codigo);
        initCause(cause);
    }
}
