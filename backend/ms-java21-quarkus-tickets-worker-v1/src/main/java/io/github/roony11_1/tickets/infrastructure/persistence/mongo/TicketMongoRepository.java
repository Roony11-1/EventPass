package io.github.roony11_1.tickets.infrastructure.persistence.mongo;

import java.util.List;
import java.util.Optional;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped 
public class TicketMongoRepository implements PanacheMongoRepository<TicketDocument>
{

}
