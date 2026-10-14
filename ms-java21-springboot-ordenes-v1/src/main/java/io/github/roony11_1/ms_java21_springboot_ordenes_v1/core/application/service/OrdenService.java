package io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.domain.models.Orden;

@Service 
@Transactional 
public class OrdenService 
{
    public Orden crear(long eventoId, long compradorId)
    {
        return Orden.crear(eventoId, compradorId);
    }

    @Transactional(readOnly = true)
    public Orden buscarPorId(Long id)
    {
        return null; // TODO: Capa de persistencia
    }


    public List<Orden> buscarPorComprador(long compradorId)
    {
        return null; // TODO: Capa de persistencia
    }

    public List<Orden> buscarPorEvento(long eventoId)
    {
        return null; // TODO: Capa de persistencia
    }

    @Transactional
    public Orden marcarEmitida(Long id, long ticketId)
    {
        var orden = buscarPorId(id);

        orden.marcarEmitida(ticketId);

        return orden;
    }

    @Transactional
    public Orden marcarRechazada(Long id)
    {
        var orden = buscarPorId(id);

        orden.marcarRechazada();

        return orden;
    }
}
