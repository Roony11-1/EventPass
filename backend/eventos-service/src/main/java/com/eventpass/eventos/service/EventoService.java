package com.eventpass.eventos.service;

import com.eventpass.eventos.dto.*;
import com.eventpass.eventos.model.Evento;
import com.eventpass.eventos.model.TipoEntrada;
import com.eventpass.eventos.repository.EventoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepository eventoRepository;

    @Transactional
    public EventoResponse crear(EventoRequest request) {
        Evento evento = new Evento();
        evento.setNombre(request.getNombre());
        evento.setDescripcion(request.getDescripcion());
        evento.setFecha(request.getFecha());
        evento.setLugar(request.getLugar());
        evento.setAforoTotal(request.getAforoTotal());
        evento.setAforoDisponible(request.getAforoTotal());

        if (request.getTiposEntrada() != null) {
            for (TipoEntradaRequest tipoReq : request.getTiposEntrada()) {
                TipoEntrada tipo = new TipoEntrada();
                tipo.setNombre(tipoReq.getNombre());
                tipo.setPrecio(tipoReq.getPrecio());
                tipo.setEvento(evento);
                evento.getTiposEntrada().add(tipo);
            }
        }

        Evento guardado = eventoRepository.save(evento);
        return toResponse(guardado);
    }

    @Transactional(readOnly = true)
    public List<EventoResponse> listar() {
        return eventoRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EventoResponse buscarPorId(Long id) {
        Evento evento = buscarEntidadPorId(id);
        return toResponse(evento);
    }

    @Transactional
    public EventoResponse actualizar(Long id, EventoRequest request) {
        Evento evento = buscarEntidadPorId(id);
        int vendidas = evento.getAforoTotal() - evento.getAforoDisponible();

        evento.setNombre(request.getNombre());
        evento.setDescripcion(request.getDescripcion());
        evento.setFecha(request.getFecha());
        evento.setLugar(request.getLugar());
        evento.setAforoTotal(request.getAforoTotal());
        evento.setAforoDisponible(Math.max(0, request.getAforoTotal() - vendidas));

        return toResponse(eventoRepository.save(evento));
    }

    @Transactional
    public void eliminar(Long id) {
        Evento evento = buscarEntidadPorId(id);
        eventoRepository.delete(evento);
    }

    @Transactional
    public ReservaResponse reservar(Long id, ReservaRequest request) {
        Evento evento = buscarEntidadPorId(id);

        if (evento.getAforoDisponible() < request.getCantidad()) {
            return new ReservaResponse(false, evento.getAforoDisponible());
        }

        evento.setAforoDisponible(evento.getAforoDisponible() - request.getCantidad());

        try {
            eventoRepository.saveAndFlush(evento);
        } catch (ObjectOptimisticLockingFailureException e) {
            Integer aforoActual = buscarEntidadPorId(id).getAforoDisponible();
            return new ReservaResponse(false, aforoActual);
        }

        return new ReservaResponse(true, evento.getAforoDisponible());
    }

    private Evento buscarEntidadPorId(Long id) {
        return eventoRepository.findById(id)
                .orElseThrow(() -> new EventoNoEncontradoException("No existe un evento con id " + id));
    }

    private EventoResponse toResponse(Evento evento) {
        List<TipoEntradaResponse> tipos = evento.getTiposEntrada().stream()
                .map(t -> new TipoEntradaResponse(t.getId(), t.getNombre(), t.getPrecio()))
                .collect(Collectors.toList());

        return new EventoResponse(
                evento.getId(),
                evento.getNombre(),
                evento.getDescripcion(),
                evento.getFecha(),
                evento.getLugar(),
                evento.getAforoTotal(),
                evento.getAforoDisponible(),
                tipos
        );
    }
}