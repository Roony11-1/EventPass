package io.github.roony11_1.ms_java21_springboot_ordenes_v1.infrastructure.persistence.mongo;

import java.util.List;

import org.springframework.stereotype.Component;

import io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.domain.models.Orden;
import io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.domain.repository.IOrdenRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrdenMongoAdapter implements IOrdenRepository
{
    private final OrdenMongoRepository ordenMongoRepository;
    private final OrdenMapper ordenMapper;

    @Override
    public Orden guardar(Orden orden)
    {
        OrdenDocument guardado = ordenMongoRepository.save(ordenMapper.aDocumento(orden));
        return ordenMapper.aDominio(guardado);
    }

    @Override
    public Optional<Orden> buscarPorId(String id)
    {
        return ordenMongoRepository.findById(id)
            .map(ordenMapper::aDominio);
    }

    @Override
    public List<Orden> buscarPorComprador(long compradorId)
    {
        return ordenMongoRepository.findByCompradorId(compradorId).stream()
            .map(ordenMapper::aDominio)
            .toList();
    }

    @Override
    public List<Orden> buscarPorEvento(long eventoId)
    {
        return ordenMongoRepository.findByEventoId(eventoId).stream()
            .map(ordenMapper::aDominio)
            .toList();
    }
}
