package com.tecsup.app.micro.order.domain.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(Long id) {
        super("Orden no encontrada con id: " + id);
    }
}