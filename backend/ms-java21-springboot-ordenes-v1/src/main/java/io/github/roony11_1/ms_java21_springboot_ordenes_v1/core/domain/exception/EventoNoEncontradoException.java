package io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.domain.exception;

import io.github.roony11_1.error.core.exceptions.AppException;

public class EventoNoEncontradoException extends AppException
{
    public EventoNoEncontradoException(String detalle)
    {
        super("ORD-404", "Evento no encontrado: " + detalle, OrdenErrorCategory.NOT_FOUND, "Evento no encontrado: " + detalle);
    }
    public EventoNoEncontradoException(String detalle, Throwable cause)
    {
        super("ORD-404", "Evento no encontrado: " + detalle, OrdenErrorCategory.NOT_FOUND, "Evento no encontrado: " + detalle, cause);
    }
}
