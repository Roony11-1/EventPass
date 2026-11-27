package io.github.roony11_1.tickets.core.domain.exception;

import io.github.roony11_1.error.core.ErrorCategory;

public enum TicketErrorCategory implements ErrorCategory
{
    NOT_FOUND("Ticket no encontrado"),
    INVALID("Ticket inválido"),
    STATE_CONFLICT("Transición de estado no permitida"),
    CONFLICT("Ticket ya utilizado");

    private final String description;
    TicketErrorCategory(String description) { this.description = description; }

    @Override
    public String description() { return description; }
}
