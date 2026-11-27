package io.github.roony11_1.tickets.core.domain.exception;

public class TicketInvalidoException extends RuntimeException
{
    public TicketInvalidoException(String detalle)
    {
        super("Ticket inválido: " + detalle);
    }

    public TicketInvalidoException(String detalle, Throwable cause)
    {
        super("Ticket inválido: " + detalle, cause);
    }
}
