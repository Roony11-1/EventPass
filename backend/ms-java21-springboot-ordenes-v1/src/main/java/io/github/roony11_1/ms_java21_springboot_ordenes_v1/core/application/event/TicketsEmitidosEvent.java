package io.github.roony11_1.ms_java21_springboot_ordenes_v1.core.application.event;

import java.time.Instant;
import java.util.List;

public record TicketsEmitidosEvent(String ordenId, List<String> ticketIds, Instant emittedAt) {}
