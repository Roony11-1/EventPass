package io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.domain.exception.OrdenNoEncontradaException;
import io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.domain.models.Orden;
import io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.domain.repository.IOrdenRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrdenService
{
    private final IOrdenRepository ordenRepository;

    public Orden crear(Long eventoId, Long compradorId)
    {
        Orden orden = Orden.crear(eventoId, compradorId);

        return ordenRepository.guardar(orden);
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

        return ordenRepository
            .guardar(orden);
    }

    public Orden marcarRechazada(String id)
    {
        Orden orden = buscarPorId(id);

        orden.marcarRechazada();

        return ordenRepository
            .guardar(orden);
    }
}
