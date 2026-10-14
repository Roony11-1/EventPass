package io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.domain.exception;

import io.github.roony11_1.error.core.exceptions.AppException;
import io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.domain.models.EstadoOrden;

public class TransicionEstadoNoPermitidaException extends AppException 
{
    public TransicionEstadoNoPermitidaException(EstadoOrden desde, EstadoOrden hacia) 
    {
        super("ORD-422", "No se puede pasar de " + desde + " a " + hacia, OrdenErrorCategory.STATE_CONFLICT, "No se puede pasar de " + desde + " a " + hacia);
    }
    public TransicionEstadoNoPermitidaException(EstadoOrden desde, EstadoOrden hacia, Throwable cause) 
    {
        super("ORD-422", "No se puede pasar de " + desde + " a " + hacia, OrdenErrorCategory.STATE_CONFLICT, "No se puede pasar de " + desde + " a " + hacia, cause);
    }
}
