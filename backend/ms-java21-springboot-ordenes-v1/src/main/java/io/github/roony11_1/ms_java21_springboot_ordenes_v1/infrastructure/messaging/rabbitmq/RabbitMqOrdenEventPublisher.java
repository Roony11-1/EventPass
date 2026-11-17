package io.github.roony11_1.ms_java21_springboot_ordenes_v1.infrastructure.messaging.rabbitmq;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.application.event.IOrdenEventPublisher;
import io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.application.event.OrdenCreadaEvent;
import io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.domain.exception.MensajeriaNoDisponibleException;
import io.github.roony11_1.ms_java21_springboot_ordenes_v1.infrastructure.config.RabbitConfig;
import lombok.RequiredArgsConstructor;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.json.JsonMapper;

@Component 
@RequiredArgsConstructor 
public class RabbitMqOrdenEventPublisher implements IOrdenEventPublisher
{
    private final RabbitTemplate rabbitTemplate;
    private final JsonMapper jsonMapper;

    @Override
    public void publicarOrdenCreada(OrdenCreadaEvent event)
    {
        try
        {
            String payload = jsonMapper.writeValueAsString(event);
            
            rabbitTemplate.convertAndSend(
                RabbitConfig.EXCHANGE, 
                RabbitConfig.RK_ORDEN_CREADA, 
                payload,
                mensaje -> 
                {
                    mensaje.getMessageProperties().setContentType("application/json");
                    return mensaje;
                });
        }
        catch (JacksonException | AmqpException e)
        {
            throw new MensajeriaNoDisponibleException("no se pudo publicar orden.creada de " + event.ordenId(), e);
        }
    }
}
