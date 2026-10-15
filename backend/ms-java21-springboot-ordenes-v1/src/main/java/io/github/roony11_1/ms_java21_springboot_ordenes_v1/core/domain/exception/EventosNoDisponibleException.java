package io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.domain.exception;

import io.github.roony11_1.error.core.exceptions.AppException;

public class EventosNoDisponibleException extends AppException
{
    public EventosNoDisponibleException(String detalle)
    {
        super("ORD-503", "Eventos no disponible: " + detalle, OrdenErrorCategory.INTEGRATION_UNAVAILABLE, "Eventos no disponible: " + detalle);
    }
    public EventosNoDisponibleException(String detalle, Throwable cause)
    {
        super("ORD-503", "Eventos no disponible: " + detalle, OrdenErrorCategory.INTEGRATION_UNAVAILABLE, "Eventos no disponible: " + detalle, cause);
    }
}
