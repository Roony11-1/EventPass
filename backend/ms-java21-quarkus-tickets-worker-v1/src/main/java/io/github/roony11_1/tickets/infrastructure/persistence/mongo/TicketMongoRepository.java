package io.github.roony11_1.tickets.infrastructure.persistence.mongo;

import java.util.List;

import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TicketMongoRepository implements ReactivePanacheMongoRepository<TicketDocument>
{
    public Uni<List<TicketDocument>> buscarPorOrden(String ordenId)
    {
        return find("ordenId", ordenId).list();
    }
}
