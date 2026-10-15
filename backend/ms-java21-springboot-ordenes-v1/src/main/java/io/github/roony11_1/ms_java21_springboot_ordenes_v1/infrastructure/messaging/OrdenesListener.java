package io.github.roony11_1.ms_java21_springboot_ordenes_v1.infrastructure.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.application.service.OrdenService;
import io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.application.event.TicketsEmitidosEvent;
import io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.domain.exception.TransicionEstadoNoPermitidaException;
import io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.domain.models.EstadoOrden;
import io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.domain.models.Orden;
import io.github.roony11_1.ms_java21_springboot_ordenes_v1.infrastructure.config.RabbitConfig;
import lombok.RequiredArgsConstructor;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.json.JsonMapper;

@Component
@RequiredArgsConstructor
public class OrdenesListener
{
    private final OrdenService ordenService;
    private final JsonMapper jsonMapper;

    @RabbitListener(queues = RabbitConfig.Q_TICKETS_EMITIDOS)
    public void onTicketsEmitidos(byte[] payload)
    {
        final TicketsEmitidosEvent evento;

        try
        {
            evento = jsonMapper.readValue(payload, TicketsEmitidosEvent.class);
        }
        catch (JacksonException e)
        {
            // mensaje malformado: nunca va a procesarse, ack para no reencolar veneno
            return;
        }

        try
        {
            Orden orden = ordenService.buscarPorId(evento.ordenId());

            if (orden.getEstado() == EstadoOrden.EMITIDA)
                return;

            ordenService.marcarEmitida(evento.ordenId(), evento.ticketIds());
        }
        catch (TransicionEstadoNoPermitidaException e)
        {
            // estado terminal (ej. RECHAZADA): nada que hacer, ack para no reencolar veneno
        }
    }
}
