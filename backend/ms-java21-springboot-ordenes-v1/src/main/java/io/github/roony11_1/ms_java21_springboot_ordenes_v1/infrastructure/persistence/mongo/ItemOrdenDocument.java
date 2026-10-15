package io.github.roony11_1.ms_java21_springboot_ordenes_v1.infrastructure.persistence.mongo;

import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
public class ItemOrdenDocument
{
    @Field("tipo_entrada_id")
    private long tipoEntradaId;

    @Field("cantidad")
    private int cantidad;
}
