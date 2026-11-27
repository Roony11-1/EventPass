package io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.application.event;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component 
@RequiredArgsConstructor 
public class TicketsEmitidosHandler 
{
    private final ITicketEmitidoProcesor ticketEmitidoProcesor;

    public void handle(TicketsEmitidosEvent evento)
    {
        ticketEmitidoProcesor.procesar(evento);
    }
}
