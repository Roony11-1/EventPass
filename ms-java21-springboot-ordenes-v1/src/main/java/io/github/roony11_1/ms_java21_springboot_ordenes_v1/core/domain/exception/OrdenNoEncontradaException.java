package io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.domain.exception;

import io.github.roony11_1.error.core.exceptions.AppException;

public class OrdenNoEncontradaException extends AppException 
{
    public OrdenNoEncontradaException(Long id) 
    {
        super("ORD-404", "Orden no encontrada: " + id, OrdenErrorCategory.NOT_FOUND, "Orden no encontrada: " + id);
    }

    public OrdenNoEncontradaException(Long id, Throwable cause) 
    {
        super("ORD-404", "Orden no encontrada: " + id, OrdenErrorCategory.NOT_FOUND, "Orden no encontrada: " + id, cause);
    }
}
