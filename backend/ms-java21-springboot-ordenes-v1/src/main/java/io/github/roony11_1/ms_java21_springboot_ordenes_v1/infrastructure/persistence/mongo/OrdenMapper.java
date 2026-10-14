package io.github.roony11_1.ms_java21_springboot_ordenes_v1.infrastructure.persistence.mongo;

import org.springframework.stereotype.Component;

import io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.domain.models.Orden;

@Component 
public final class OrdenMapper
{
    public OrdenDocument aDocumento(Orden o)
    {
        OrdenDocument d = new OrdenDocument();
        
        d.setId(o.getId());
        d.setEventoId(o.getEventoId());
        d.setCompradorId(o.getCompradorId());
        d.setTicketId(o.getTicketId());
        d.setEstado(o.getEstado());
        d.setCreatedAt(o.getCreatedAt());
        d.setUpdatedAt(o.getUpdatedAt());

        return d;
    }

    public Orden aDominio(OrdenDocument d)
    {
        return Orden.rehidratar(
                d.getId(),
                d.getEventoId(),
                d.getCompradorId(),
                d.getTicketId(),
                d.getEstado(),
                d.getCreatedAt(),
                d.getUpdatedAt());
    }
}
