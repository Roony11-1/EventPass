package io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.domain.exception;

import io.github.roony11_1.error.core.exceptions.AppException;

public class ConflictoOptimistaException extends AppException
{
    public ConflictoOptimistaException(String detalle)
    {
        super("ORD-409", "Conflicto de concurrencia: " + detalle, OrdenErrorCategory.CONFLICT, "Conflicto de concurrencia: " + detalle);
    }
    public ConflictoOptimistaException(String detalle, Throwable cause)
    {
        super("ORD-409", "Conflicto de concurrencia: " + detalle, OrdenErrorCategory.CONFLICT, "Conflicto de concurrencia: " + detalle, cause);
    }
}
