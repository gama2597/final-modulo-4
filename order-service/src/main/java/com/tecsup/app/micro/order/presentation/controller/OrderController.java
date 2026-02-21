package com.tecsup.app.micro.order.presentation.controller;

import com.tecsup.app.micro.order.application.service.OrderApplicationService;
import com.tecsup.app.micro.order.domain.model.Order;
import com.tecsup.app.micro.order.presentation.dto.CreateOrderRequest;
import com.tecsup.app.micro.order.presentation.dto.OrderResponse;
import com.tecsup.app.micro.order.presentation.mapper.OrderDtoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderApplicationService orderApplicationService;
    private final OrderDtoMapper orderDtoMapper;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        log.info("REST para crear orden: {}", request.getUserId());

        Order orderToCreate = orderDtoMapper.toDomain(request);
        Order createdOrder = orderApplicationService.createOrder(orderToCreate);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderDtoMapper.toResponse(createdOrder));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        log.info("REST para obtener todas las ordenes");
        List<Order> orders = orderApplicationService.getAllOrders();
        return ResponseEntity.ok(orderDtoMapper.toResponseList(orders));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {
        log.info("REST para obtener orden por ID: {}", id);
        Order order = orderApplicationService.getOrderById(id);
        return ResponseEntity.ok(orderDtoMapper.toResponse(order));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponse>> getOrdersByUser(@PathVariable Long userId) {
        log.info("REST para obtener ordenes por usuario: {}", userId);
        List<Order> orders = orderApplicationService.getOrdersByUser(userId);
        return ResponseEntity.ok(orderDtoMapper.toResponseList(orders));
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Order Service running with Clean Architecture!");
    }
}