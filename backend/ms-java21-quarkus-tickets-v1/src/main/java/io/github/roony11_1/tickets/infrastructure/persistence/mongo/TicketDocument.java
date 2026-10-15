package io.github.roony11_1.tickets.infrastructure.persistence.mongo;

import java.time.Instant;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;

@MongoEntity (collection = "tickets")
public class TicketDocument extends PanacheMongoEntity
{
    public String codigo;
    public String ordenId;
    public long eventoId;
    public long tipoEntradaId;
    public String estado;
    public Instant createdAt;
}
