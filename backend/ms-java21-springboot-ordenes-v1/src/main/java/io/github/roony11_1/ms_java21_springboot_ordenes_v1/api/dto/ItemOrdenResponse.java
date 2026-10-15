package io.github.roony11_1.ms_java21_springboot_ordenes_v1.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemOrdenResponse
{
    private long tipoEntradaId;
    private int cantidad;
}
