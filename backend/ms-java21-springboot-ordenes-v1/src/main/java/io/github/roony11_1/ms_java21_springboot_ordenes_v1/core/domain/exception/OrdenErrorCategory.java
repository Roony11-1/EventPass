package io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.domain.exception;

import io.github.roony11_1.error.core.ErrorCategory;

public enum OrdenErrorCategory implements  ErrorCategory
{
    NOT_FOUND("Orden no encontrada"),
    INVALID("Orden inválida"),
    STATE_CONFLICT("Transición de estado no permitida"),
    CONFLICT("Conflicto de stock o concurrencia"),
    INTEGRATION_UNAVAILABLE("Dependencia no disponible");

    private final String description;
    OrdenErrorCategory(String description) { this.description = description; }

    @Override
    public String description() { return description; }
}
