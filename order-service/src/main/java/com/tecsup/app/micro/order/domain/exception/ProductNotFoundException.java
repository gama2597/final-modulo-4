package com.tecsup.app.micro.order.domain.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super("Producto no encontrado en el servicio externo con id: " + id);
    }
}