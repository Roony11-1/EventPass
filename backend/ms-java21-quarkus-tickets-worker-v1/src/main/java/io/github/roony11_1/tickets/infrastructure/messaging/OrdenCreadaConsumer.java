package io.github.roony11_1.tickets.infrastructure.messaging;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Metadata;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.roony11_1.tickets.core.application.event.IOrdenCreadaProcesor;
import io.github.roony11_1.tickets.core.application.event.OrdenCreadaEvent;
import io.github.roony11_1.tickets.core.application.event.TicketsEmitidosEvent;
import io.smallrye.reactive.messaging.annotations.Blocking;
import io.smallrye.reactive.messaging.rabbitmq.OutgoingRabbitMQMetadata;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class OrdenCreadaConsumer
{
    private final IOrdenCreadaProcesor ordenCreadaProcesor;
    private final ObjectMapper objectMapper;

    @Inject
    @Channel("tickets-emitidos")
    Emitter<TicketsEmitidosEvent> emitter;

    @Incoming("ordenes-creada")
    @Blocking
    public CompletionStage<Void> consumir(Message<byte[]> mensaje)
    {
        var evento = deserialize(mensaje);

        if (!evento.isPresent())
            return mensaje.nack(null);

        var emitidos = ordenCreadaProcesor.procesar(evento.get());

        if (emitidos.isEmpty())
            return mensaje.nack(null);

        OrdenCreadaEvent ordenCreada = evento.get();

        publicar(ordenCreada.ordenId(), emitidos.stream().map(t -> t.getId()).toList());

        return mensaje.ack();
    }

    private Optional<OrdenCreadaEvent> deserialize(Message<byte[]> message)
    {
        try
        {
            var event = objectMapper.readValue(message.getPayload(), OrdenCreadaEvent.class);

            return Optional.of(event);
        }
        catch (Exception e)
        {
            return Optional.empty();
        }
    }

    private void publicar(String ordenId, List<String> ticketIds)
    {
        var metadata = Metadata.of(OutgoingRabbitMQMetadata.builder()
            .withRoutingKey("tickets.emitidos")
            .withContentType("application/json")
            .build());

        emitter.send(Message.of(new TicketsEmitidosEvent(ordenId, ticketIds, Instant.now()), metadata));
    }

}
