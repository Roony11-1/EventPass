package com.eventpass.eventos.repository;

import com.eventpass.eventos.model.TipoEntrada;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoEntradaRepository extends JpaRepository<TipoEntrada, Long> {
}