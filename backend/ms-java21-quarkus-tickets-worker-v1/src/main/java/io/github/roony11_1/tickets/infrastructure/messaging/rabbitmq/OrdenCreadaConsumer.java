
package io.github.roony11_1.tickets.infrastructure.messaging.rabbitmq;

import java.io.IOException;
import java.util.concurrent.CompletionStage;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.roony11_1.tickets.core.application.event.OrdenCreadaEvent;
import io.github.roony11_1.tickets.core.application.event.OrdenCreadaProcesor;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class OrdenCreadaConsumer
{
    private final OrdenCreadaProcesor ordenCreadaProcesor;
    private final TicketsEmitidosPublisher ticketsEmitidosPublisher;
    private final ObjectMapper objectMapper;

    @Incoming("ordenes-creadas")
    public CompletionStage<Void> consumir(Message<byte[]> mensaje)
    {
        final OrdenCreadaEvent evento;

        try
        {
            evento = objectMapper.readValue(
                mensaje.getPayload(),
                OrdenCreadaEvent.class
            );
        }
        catch (IOException e)
        {
            return mensaje.nack(e);
        }

        return ordenCreadaProcesor.procesar(evento)
            .onItem().transformToUni(emitidos ->
            {
                if (emitidos.isEmpty())
                    return Uni.createFrom().voidItem();

                var ticketsIds = emitidos.stream()
                    .map(ticket -> ticket.getId())
                    .toList();

                return Uni.createFrom().completionStage(
                    ticketsEmitidosPublisher.publicar(
                        evento.ordenId(),
                        ticketsIds
                    )
                );
            })
            .subscribeAsCompletionStage()
            .thenCompose(ignored -> mensaje.ack());
    }
}