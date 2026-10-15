package io.github.roony11_1.ms_java21_springboot_ordenes_v1.infrastructure.persistence.mongo;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.domain.models.EstadoOrden;
import lombok.Data;

@Document(collection = "ordenes")
@Data
public class OrdenDocument
{
    @Id
    private String id;

    @Field("evento_id")
    private long eventoId;

    @Field("comprador_id")
    private long compradorId;

    @Field("items")
    private List<ItemOrdenDocument> items = new ArrayList<>();

    @Field("ticket_ids")
    private List<String> ticketIds = new ArrayList<>();

    private EstadoOrden estado;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    @Version
    private Long version;
}
