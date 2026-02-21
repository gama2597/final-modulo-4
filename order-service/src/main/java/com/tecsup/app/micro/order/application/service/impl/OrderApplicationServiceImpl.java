package com.tecsup.app.micro.order.application.service.impl;

import com.tecsup.app.micro.order.application.service.OrderApplicationService;
import com.tecsup.app.micro.order.application.usecase.CreateOrderUseCase;
import com.tecsup.app.micro.order.application.usecase.GetAllOrdersUseCase;
import com.tecsup.app.micro.order.application.usecase.GetOrderByIdUseCase;
import com.tecsup.app.micro.order.application.usecase.GetOrdersByUserUseCase;
import com.tecsup.app.micro.order.domain.model.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementación del Servicio de Aplicación.
 * Actúa como fachada para los casos de uso y gestiona las transacciones.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class OrderApplicationServiceImpl implements OrderApplicationService {

    private final CreateOrderUseCase createOrderUseCase;
    private final GetOrderByIdUseCase getOrderByIdUseCase;
    private final GetOrdersByUserUseCase getOrdersByUserUseCase;
    private final GetAllOrdersUseCase getAllOrdersUseCase;

    @Override
    @Transactional
    public Order createOrder(Order order) {
        return createOrderUseCase.execute(order);
    }

    @Override
    @Transactional(readOnly = true)
    public Order getOrderById(Long id) {
        return getOrderByIdUseCase.execute(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getOrdersByUser(Long userId) {
        return getOrdersByUserUseCase.execute(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        return getAllOrdersUseCase.execute();
    }
}
