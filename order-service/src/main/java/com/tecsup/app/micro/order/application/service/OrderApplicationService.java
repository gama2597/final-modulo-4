package com.tecsup.app.micro.order.application.service;

import com.tecsup.app.micro.order.domain.model.Order;
import java.util.List;

public interface OrderApplicationService {

    /**
     * Crea una nueva orden
     */
    Order createOrder(Order order);

    /**
     * Busca una orden por su ID
     */
    Order getOrderById(Long id);

    /**
     * Lista las órdenes de un usuario específico
     */
    List<Order> getOrdersByUser(Long userId);

    List<Order> getAllOrders();
}