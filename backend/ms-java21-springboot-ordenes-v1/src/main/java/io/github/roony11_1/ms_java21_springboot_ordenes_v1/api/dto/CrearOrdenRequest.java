package io.github.roony11_1.ms_java21_springboot_ordenes_v1.api.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
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
public class CrearOrdenRequest
{
    @NotNull(message = "eventoId es obligatorio")
    @Positive(message = "eventoId debe ser > 0")
    private Long eventoId;

    @NotNull(message = "compradorId es obligatorio")
    @Positive(message = "compradorId debe ser > 0")
    private Long compradorId;

    @NotEmpty(message = "items es obligatorio")
    @Valid
    private List<ItemOrdenRequest> items;
}
