package io.github.roony11_1.ms_java21_springboot_ordenes_v1.infrastructure.persistence.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.domain.models.EstadoOrden;

public interface OrdenMongoRepository extends MongoRepository<OrdenDocument, String>
{
    List<OrdenDocument> findByCompradorId(long compradorId);

    List<OrdenDocument> findByEventoId(long eventoId);

    List<OrdenDocument> findByEstado(EstadoOrden estado);
}
