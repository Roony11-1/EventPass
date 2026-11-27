package io.github.roony11_1.ms_java21_springboot_ordenes_v1.infrastructure.messaging.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.application.event.TicketsEmitidosEvent;
import io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.application.event.TicketsEmitidosHandler;
import io.github.roony11_1.ms_java21_springboot_ordenes_v1.infrastructure.config.RabbitConfig;
import lombok.RequiredArgsConstructor;
import tools.jackson.databind.json.JsonMapper;

@Component 
@RequiredArgsConstructor 
public class RabbitMqOrdenesListener 
{
    private final JsonMapper jsonMapper;
    private final TicketsEmitidosHandler handler;

    @RabbitListener(queues = RabbitConfig.Q_TICKETS_EMITIDOS)
    public void onTicketsEmitidos(byte[] payload)
    {
        final TicketsEmitidosEvent evento = jsonMapper.readValue(payload, TicketsEmitidosEvent.class);

        handler.handle(evento);
    }
}
