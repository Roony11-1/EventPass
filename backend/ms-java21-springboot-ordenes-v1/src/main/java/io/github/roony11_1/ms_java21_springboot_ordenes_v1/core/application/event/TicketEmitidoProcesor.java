package io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.application.event;

import org.springframework.stereotype.Component;

import io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.application.service.OrdenService;
import io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.domain.exception.TransicionEstadoNoPermitidaException;
import io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.domain.models.EstadoOrden;
import io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.domain.models.Orden;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor 
public class TicketEmitidoProcesor implements  ITicketEmitidoProcesor
{
    private final OrdenService ordenService;

    @Override
    public void procesar(TicketsEmitidosEvent evento) 
    {
        try
        {
            Orden orden = ordenService.buscarPorId(evento.ordenId());

            if (orden.getEstado() == EstadoOrden.EMITIDA)
                return;

            ordenService.marcarEmitida(evento.ordenId(), evento.ticketIds());
        }
        catch (TransicionEstadoNoPermitidaException e)
        {
            // Resolver según la regla de negocio correspondiente.
        }
    }
}
