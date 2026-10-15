package io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.domain.exception;

import io.github.roony11_1.error.core.exceptions.AppException;

public class StockInsuficienteException extends AppException
{
    public StockInsuficienteException(String detalle)
    {
        super("ORD-409", "Stock insuficiente: " + detalle, OrdenErrorCategory.CONFLICT, "Stock insuficiente: " + detalle);
    }
    public StockInsuficienteException(String detalle, Throwable cause)
    {
        super("ORD-409", "Stock insuficiente: " + detalle, OrdenErrorCategory.CONFLICT, "Stock insuficiente: " + detalle, cause);
    }
}
