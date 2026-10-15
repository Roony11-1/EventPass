package io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.application.service;

import java.util.List;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import feign.FeignException;
import io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.application.event.OrdenCreadaEvent;
import io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.application.event.OrdenCreadaEvent.ItemCreado;
import io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.domain.exception.ConflictoOptimistaException;
import io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.domain.exception.EventoNoEncontradoException;
import io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.domain.exception.EventosNoDisponibleException;
import io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.domain.exception.MensajeriaNoDisponibleException;
import io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.domain.exception.OrdenNoEncontradaException;
import io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.domain.exception.StockInsuficienteException;
import io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.domain.models.ItemOrden;
import io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.domain.models.Orden;
import io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.domain.repository.IOrdenRepository;
import io.github.roony11_1.ms_java21_springboot_ordenes_v1.infrastructure.client.EventoReservaRequest;
import io.github.roony11_1.ms_java21_springboot_ordenes_v1.infrastructure.client.EventoReservaResponse;
import io.github.roony11_1.ms_java21_springboot_ordenes_v1.infrastructure.client.EventosClient;
import io.github.roony11_1.ms_java21_springboot_ordenes_v1.infrastructure.config.RabbitConfig;
import lombok.RequiredArgsConstructor;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.json.JsonMapper;

@Service
@RequiredArgsConstructor
public class OrdenService
{
    private final IOrdenRepository ordenRepository;
    private final EventosClient eventosClient;
    private final RabbitTemplate rabbitTemplate;
    private final JsonMapper jsonMapper;

    public Orden crear(Long eventoId, Long compradorId, List<ItemOrden> items)
    {
        Orden orden = Orden.crear(eventoId, compradorId, items);

        reservarStock(orden.getEventoId(), orden.getCantidadTotal());

        Orden guardada = guardar(orden);

        publicarOrdenCreada(guardada);

        return guardada;
    }

    public Orden buscarPorId(String id)
    {
        return ordenRepository.buscarPorId(id)
            .orElseThrow(() -> new OrdenNoEncontradaException(id));
    }

    public List<Orden> buscarPorComprador(long compradorId)
    {
        return ordenRepository
            .buscarPorComprador(compradorId);
    }

    public List<Orden> buscarPorEvento(long eventoId)
    {
        return ordenRepository
            .buscarPorEvento(eventoId);
    }

    public Orden marcarEmitida(String id, List<String> ticketIds)
    {
        Orden orden = buscarPorId(id);

        orden.marcarEmitida(ticketIds);

        return guardar(orden);
    }

    public Orden marcarRechazada(String id)
    {
        Orden orden = buscarPorId(id);

        orden.marcarRechazada();

        return guardar(orden);
    }

    private void reservarStock(long eventoId, int cantidad)
    {
        EventoReservaResponse respuesta;

        try
        {
            respuesta = eventosClient.reservar(eventoId, new EventoReservaRequest(cantidad));
        }
        catch (FeignException.NotFound e)
        {
            throw new EventoNoEncontradoException(String.valueOf(eventoId), e);
        }
        catch (FeignException e)
        {
            throw new EventosNoDisponibleException("no se pudo reservar stock del evento " + eventoId, e);
        }

        if (!respuesta.isReservado())
            throw new StockInsuficienteException("evento " + eventoId + " cantidad " + cantidad
                + " aforo disponible " + respuesta.getAforoDisponible());
    }

    private Orden guardar(Orden orden)
    {
        try
        {
            return ordenRepository.guardar(orden);
        }
        catch (OptimisticLockingFailureException e)
        {
            throw new ConflictoOptimistaException("la orden " + orden.getId() + " fue modificada por otro proceso", e);
        }
    }

    private void publicarOrdenCreada(Orden orden)
    {
        OrdenCreadaEvent evento = new OrdenCreadaEvent(
            orden.getId(),
            orden.getEventoId(),
            orden.getCompradorId(),
            orden.getItems().stream()
                .map(item -> new ItemCreado(item.getTipoEntradaId(), item.getCantidad()))
                .toList(),
            orden.getCreatedAt());

        try
        {
            String payload = jsonMapper.writeValueAsString(evento);
            rabbitTemplate.convertAndSend(
                RabbitConfig.EXCHANGE, RabbitConfig.RK_ORDEN_CREADA, payload,
                mensaje -> {
                    mensaje.getMessageProperties().setContentType("application/json");
                    return mensaje;
                });
        }
        catch (JacksonException | AmqpException e)
        {
            throw new MensajeriaNoDisponibleException("no se pudo publicar orden.creada de " + orden.getId(), e);
        }
    }
}
