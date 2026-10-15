package io.github.roony11_1.tickets.infrastructure.config;

import io.github.roony11_1.error.core.ErrorCategory;
import io.github.roony11_1.error.quarkus.ErrorCategoryHttpMapper;
import io.github.roony11_1.tickets.core.domain.exception.TicketErrorCategory;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TicketHttpMapper implements ErrorCategoryHttpMapper
{
    @Override
    public int toHttpStatus(ErrorCategory category)
    {
        if (category == TicketErrorCategory.NOT_FOUND)
            return 404;
        if (category == TicketErrorCategory.INVALID)
            return 400;
        if (category == TicketErrorCategory.STATE_CONFLICT)
            return 422;
        return 500;
    }
}
