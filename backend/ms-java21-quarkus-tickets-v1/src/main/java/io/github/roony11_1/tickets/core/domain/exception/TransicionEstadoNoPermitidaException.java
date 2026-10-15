package io.github.roony11_1.tickets.core.domain.exception;

import io.github.roony11_1.error.core.exceptions.AppException;
import io.github.roony11_1.tickets.core.domain.models.TicketEstado;

public class TransicionEstadoNoPermitidaException extends AppException
{
    public TransicionEstadoNoPermitidaException(TicketEstado desde, TicketEstado hacia)
    {
        super("TIC-422", "No se puede pasar de " + desde + " a " + hacia, TicketErrorCategory.STATE_CONFLICT, "No se puede pasar de " + desde + " a " + hacia);
    }

    public TransicionEstadoNoPermitidaException(TicketEstado desde, TicketEstado hacia, Throwable cause)
    {
        super("TIC-422", "No se puede pasar de " + desde + " a " + hacia, TicketErrorCategory.STATE_CONFLICT, "No se puede pasar de " + desde + " a " + hacia);
        initCause(cause);
    }
}
