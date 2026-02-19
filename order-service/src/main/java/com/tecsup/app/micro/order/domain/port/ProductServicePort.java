package com.tecsup.app.micro.order.domain.port;

import com.tecsup.app.micro.order.domain.model.Product;

public interface ProductServicePort {
    /**
     * Obtiene la info del producto desde el microservicio externo.
     */
    Product getProductById(Long id);

    // Podríamos agregar un método para reservar stock si quisiéramos ser más
    // avanzados
    // void reduceStock(Long id, Integer quantity);
}