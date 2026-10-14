package io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.domain.exception;

import io.github.roony11_1.error.core.exceptions.AppException;

public class OrdenInvalidaException extends AppException
{
    public OrdenInvalidaException(String detalle) 
    {
        super("ORD-400", "Orden inválida: " + detalle, OrdenErrorCategory.INVALID, "Orden inválida: " + detalle);
    }
    public OrdenInvalidaException(String detalle, Throwable cause) 
    {
        super("ORD-400", "Orden inválida: " + detalle, OrdenErrorCategory.INVALID, "Orden inválida: " + detalle, cause);
    }
}

