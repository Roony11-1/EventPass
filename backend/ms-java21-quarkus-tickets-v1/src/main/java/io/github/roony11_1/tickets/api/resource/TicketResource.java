package io.github.roony11_1.tickets.api.resource;

import java.util.function.Function;

import io.github.roony11_1.tickets.api.dto.TicketResponse;
import io.github.roony11_1.tickets.core.application.service.TicketService;
import io.github.roony11_1.tickets.core.domain.models.Ticket;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

@Path("/api/v1/tickets")
@Produces(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor 
public class TicketResource 
{
    private final TicketService ticketService;

    private final Function<Ticket, TicketResponse> mapper = ticket -> TicketResponse.builder()
        .id(ticket.getId())
        .codigo(ticket.getCodigo())
        .ordenId(ticket.getOrdenId())
        .eventoId(ticket.getEventoId())
        .tipoEntradaId(ticket.getTipoEntradaId())
        .estado(ticket.getEstado())
        .createdAt(ticket.getCreatedAt())
        .build();

    @GET 
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") String id)
    {
        return Response.ok(ticketService.buscarPorId(id)).build();
    }

    @GET
    @Path("/por-orden/{ordenId}")
    public Response buscarPorOrden(@PathParam("ordenId") String ordenId)
    {
        return Response.ok(ticketService.buscarPorOrden(ordenId).stream().map(mapper::apply).toList()).build();
    }

}
