package io.github.roony11_1.ms_java21_springboot_ordenes_v1.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "eventos-service", url = "${EVENTOS_SERVICE_URL}")
public interface EventosClient
{
    @PostMapping("/api/eventos/{id}/reservar")
    EventoReservaResponse reservar(@PathVariable("id") long eventoId,
        @RequestBody EventoReservaRequest request);
}
