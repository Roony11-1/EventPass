package io.github.roony11_1.tickets.core.application.event;

import java.time.Instant;
import java.util.List;

public record TicketsEmitidosEvent(
    String ordenId,
    List<String> ticketIds,
    Instant emittedAt)
{
    
}
