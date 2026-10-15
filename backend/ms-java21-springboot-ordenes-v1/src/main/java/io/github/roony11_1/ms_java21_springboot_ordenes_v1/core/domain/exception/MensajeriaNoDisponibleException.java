package io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.domain.exception;

import io.github.roony11_1.error.core.exceptions.AppException;

public class MensajeriaNoDisponibleException extends AppException
{
    public MensajeriaNoDisponibleException(String detalle)
    {
        super("ORD-503", "Mensajería no disponible: " + detalle, OrdenErrorCategory.INTEGRATION_UNAVAILABLE, "Mensajería no disponible: " + detalle);
    }
    public MensajeriaNoDisponibleException(String detalle, Throwable cause)
    {
        super("ORD-503", "Mensajería no disponible: " + detalle, OrdenErrorCategory.INTEGRATION_UNAVAILABLE, "Mensajería no disponible: " + detalle, cause);
    }
}
