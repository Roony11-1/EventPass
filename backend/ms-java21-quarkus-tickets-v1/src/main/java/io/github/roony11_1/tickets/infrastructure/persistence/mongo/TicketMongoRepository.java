package io.github.roony11_1.tickets.infrastructure.persistence.mongo;

import java.util.List;
import java.util.Optional;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped 
public class TicketMongoRepository implements PanacheMongoRepository<TicketDocument>
{
    public List<TicketDocument> buscarPorOrden(String ordenId)
    {
        return find("ordenId", ordenId).list();
    }

    public Optional<TicketDocument> buscarPorCodigo(String codigo)
    {
        return find("codigo", codigo).firstResultOptional();
    }
}
