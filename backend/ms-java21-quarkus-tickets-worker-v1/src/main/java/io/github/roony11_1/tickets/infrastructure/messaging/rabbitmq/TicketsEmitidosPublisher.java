package io.github.roony11_1.tickets.infrastructure.messaging.rabbitmq;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.CompletionStage;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Metadata;

import io.github.roony11_1.tickets.core.application.event.TicketsEmitidosEvent;
import io.smallrye.reactive.messaging.MutinyEmitter;
import io.smallrye.reactive.messaging.rabbitmq.OutgoingRabbitMQMetadata;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

@ApplicationScoped 
@RequiredArgsConstructor 
public class TicketsEmitidosPublisher 
{
    @Channel("tickets-emitidos")
    MutinyEmitter<TicketsEmitidosEvent> emitter;

    public CompletionStage<Void> publicar(String ordenId, List<String> ticketsIds)
    {
        var evento = new TicketsEmitidosEvent(ordenId, ticketsIds, Instant.now());

        var metadata = Metadata.of(OutgoingRabbitMQMetadata.builder()
            .withRoutingKey("tickets.emitidos")
            .withContentType("application/json")
            .build());

        return emitter.sendMessage(Message.of(evento, metadata)).subscribeAsCompletionStage();
    }
}
