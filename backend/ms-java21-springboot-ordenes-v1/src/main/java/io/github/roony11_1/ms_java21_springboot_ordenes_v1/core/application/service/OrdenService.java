package io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.application.service;

import java.util.List;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import feign.FeignException;
import io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.domain.exception.ConflictoOptimistaException;
import io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.domain.exception.EventoNoEncontradoException;
import io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.domain.exception.EventosNoDisponibleException;
import io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.domain.exception.OrdenNoEncontradaException;
import io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.domain.exception.StockInsuficienteException;
import io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.domain.models.ItemOrden;
import io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.domain.models.Orden;
import io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.domain.repository.IOrdenRepository;
import io.github.roony11_1.ms_java21_springboot_ordenes_v1.infrastructure.client.EventoReservaRequest;
import io.github.roony11_1.ms_java21_springboot_ordenes_v1.infrastructure.client.EventoReservaResponse;
import io.github.roony11_1.ms_java21_springboot_ordenes_v1.infrastructure.client.EventosClient;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrdenService
{
    private final IOrdenRepository ordenRepository;
    private final EventosClient eventosClient;

    public Orden crear(Long eventoId, Long compradorId, List<ItemOrden> items)
    {
        Orden orden = Orden.crear(eventoId, compradorId, items);

        reservarStock(orden.getEventoId(), orden.getCantidadTotal());

        return guardar(orden);
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

    public Orden marcarEmitida(String id, long ticketId)
    {
        Orden orden = buscarPorId(id);

        orden.marcarEmitida(ticketId);

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
}
