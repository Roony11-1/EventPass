package io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.domain.repository;

import java.util.List;
import java.util.Optional;

import io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.domain.models.Orden;

public interface IOrdenRepository
{
    Orden guardar(Orden orden);

    Optional<Orden> buscarPorId(String id);

    List<Orden> buscarPorComprador(long compradorId);

    List<Orden> buscarPorEvento(long eventoId);
}
