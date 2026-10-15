package io.github.roony11_1.ms_java21_springboot_ordenes_v1.api.dto;

import java.time.Instant;
import java.util.List;

import io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.domain.models.EstadoOrden;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrdenResponse
{
    private String id;
    private long eventoId;
    private long compradorId;
    private List<ItemOrdenResponse> items;
    private List<String> ticketIds;
    private EstadoOrden estado;
    private Instant createdAt;
    private Instant updatedAt;
    private Long version;
}
