package io.github.roony11_1.ms_java21_springboot_ordenes_v1.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemOrdenRequest
{
    @NotNull(message = "tipoEntradaId es obligatorio")
    @Positive(message = "tipoEntradaId debe ser > 0")
    private Long tipoEntradaId;

    @NotNull(message = "cantidad es obligatoria")
    @Positive(message = "cantidad debe ser > 0")
    private Integer cantidad;
}
