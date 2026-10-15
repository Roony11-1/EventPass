package io.github.roony11_1.ms_java21_springboot_ordenes_v1.api.controller;

import java.util.function.Function;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.roony11_1.ms_java21_springboot_ordenes_v1.api.dto.CrearOrdenRequest;
import io.github.roony11_1.ms_java21_springboot_ordenes_v1.api.dto.ItemOrdenRequest;
import io.github.roony11_1.ms_java21_springboot_ordenes_v1.api.dto.ItemOrdenResponse;
import io.github.roony11_1.ms_java21_springboot_ordenes_v1.api.dto.OrdenResponse;
import io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.application.service.OrdenService;
import io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.domain.models.ItemOrden;
import io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.domain.models.Orden;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/ordenes")
@RequiredArgsConstructor
public class OrdenController
{
    private final OrdenService ordenService;

    private final Function<ItemOrden, ItemOrdenResponse> itemMapper = item -> ItemOrdenResponse.builder()
        .tipoEntradaId(item.getTipoEntradaId())
        .cantidad(item.getCantidad())
        .build();

    private final Function<Orden, OrdenResponse> mapper = orden -> OrdenResponse.builder()
        .id(orden.getId())
        .eventoId(orden.getEventoId())
        .compradorId(orden.getCompradorId())
        .items(orden.getItems().stream().map(itemMapper::apply).toList())
        .ticketIds(orden.getTicketIds())
        .estado(orden.getEstado())
        .createdAt(orden.getCreatedAt())
        .updatedAt(orden.getUpdatedAt())
        .version(orden.getVersion())
        .build();

    @PostMapping
    public ResponseEntity<OrdenResponse> crear(@Valid @RequestBody CrearOrdenRequest request)
    {
        Orden orden = ordenService.crear(
            request.getEventoId(),
            request.getCompradorId(),
            request.getItems().stream().map(this::aDominio).toList());

        return ResponseEntity.accepted().body(mapper.apply(orden));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdenResponse> buscarPorId(@PathVariable String id)
    {
        return ResponseEntity.ok(mapper.apply(ordenService.buscarPorId(id)));
    }

    private ItemOrden aDominio(ItemOrdenRequest item)
    {
        return new ItemOrden(item.getTipoEntradaId(), item.getCantidad());
    }
}
