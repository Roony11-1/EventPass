package io.github.roony11_1.tickets.api.dto;

import java.time.Instant;

import io.github.roony11_1.tickets.core.domain.models.TicketEstado;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data 
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponse
{
    private String id;
    private String codigo;
    private String ordenId;
    private long eventoId;
    private long tipoEntradaId;
    private TicketEstado estado;
    private Instant createdAt;
}