package com.eventpass.eventos.service;

public class EventoNoEncontradoException extends RuntimeException {
    public EventoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}