package io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.domain.models;

import io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.domain.exception.OrdenInvalidaException;
import lombok.Getter;

@Getter
public class ItemOrden
{
    private final long tipoEntradaId;

    private final int cantidad;

    public ItemOrden(long tipoEntradaId, int cantidad)
    {
        if (tipoEntradaId <= 0)
            throw new OrdenInvalidaException("tipoEntradaId debe ser > 0");

        if (cantidad <= 0)
            throw new OrdenInvalidaException("cantidad debe ser > 0");

        this.tipoEntradaId = tipoEntradaId;
        this.cantidad = cantidad;
    }
}
