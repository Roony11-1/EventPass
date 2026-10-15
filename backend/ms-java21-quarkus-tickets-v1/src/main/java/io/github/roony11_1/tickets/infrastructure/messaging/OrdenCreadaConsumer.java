package io.github.roony11_1.tickets.infrastructure.messaging;

import java.time.Instant;
import java.util.concurrent.CompletionStage;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Metadata;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.roony11_1.tickets.core.application.event.OrdenCreadaEvent;
import io.github.roony11_1.tickets.core.application.event.TicketsEmitidosEvent;
import io.github.roony11_1.tickets.core.application.service.TicketService;
import io.smallrye.reactive.messaging.annotations.Blocking;
import io.smallrye.reactive.messaging.rabbitmq.OutgoingRabbitMQMetadata;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class OrdenCreadaConsumer
{
    private final TicketService ticketService;
    private final ObjectMapper objectMapper;

    @Inject
    @Channel("tickets-emitidos")
    Emitter<TicketsEmitidosEvent> emitter;

    @Incoming("ordenes-creada")
    @Blocking
    public CompletionStage<Void> consumir(Message<byte[]> mensaje)
    {
        final OrdenCreadaEvent evento;

        try
        {
            evento = objectMapper.readValue(mensaje.getPayload(), OrdenCreadaEvent.class);
        }
        catch (Exception e)
        {
            return mensaje.ack();
        }

        var existentes = ticketService.buscarPorOrden(evento.ordenId());
        if (!existentes.isEmpty())
        {
            publicar(evento.ordenId(), existentes.stream().map(t -> t.getId()).toList());

            return mensaje.ack();
        }

        var emitidos = ticketService.emitirTickets(evento);

        publicar(evento.ordenId(), emitidos.stream().map(t -> t.getId()).toList());

        return mensaje.ack();
    }

    private void publicar(String ordenId, java.util.List<String> ticketIds)
    {
        var metadata = Metadata.of(OutgoingRabbitMQMetadata.builder()
            .withRoutingKey("tickets.emitidos")
            .withContentType("application/json")
            .build());

        emitter.send(Message.of(
            new TicketsEmitidosEvent(ordenId, ticketIds, Instant.now()), metadata));
    }

}
