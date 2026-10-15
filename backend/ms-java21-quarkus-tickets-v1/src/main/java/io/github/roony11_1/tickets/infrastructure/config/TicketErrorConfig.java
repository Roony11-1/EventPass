package io.github.roony11_1.tickets.infrastructure.config;

import io.github.roony11_1.error.rest.HttpStatusRegistry;
import io.github.roony11_1.tickets.core.domain.exception.TicketErrorCategory;
import io.quarkus.runtime.Startup;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

@Startup
@ApplicationScoped
public class TicketErrorConfig
{
    @PostConstruct
    void registrar()
    {
        HttpStatusRegistry.register(TicketErrorCategory.NOT_FOUND, 404);
        HttpStatusRegistry.register(TicketErrorCategory.INVALID, 400);
        HttpStatusRegistry.register(TicketErrorCategory.STATE_CONFLICT, 422);
        HttpStatusRegistry.register(TicketErrorCategory.CONFLICT, 409);
    }
}
