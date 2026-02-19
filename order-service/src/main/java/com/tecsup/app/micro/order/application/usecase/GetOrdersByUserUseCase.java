package com.tecsup.app.micro.order.application.usecase;

import com.tecsup.app.micro.order.domain.model.Order;
import com.tecsup.app.micro.order.domain.port.OrderRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetOrdersByUserUseCase {

    private final OrderRepositoryPort orderRepository;

    public List<Order> execute(Long userId) {
        log.debug("Ejecutando GetOrdersByUserUseCase para userId: {}", userId);
        // Llama al método del puerto (que implementaremos en Infrastructure)
        return orderRepository.findByUserId(userId);
    }
}