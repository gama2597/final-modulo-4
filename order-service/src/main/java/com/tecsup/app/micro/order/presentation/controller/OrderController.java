package com.tecsup.app.micro.order.presentation.controller;

import com.tecsup.app.micro.order.application.service.OrderApplicationService;
import com.tecsup.app.micro.order.domain.model.Order;
import com.tecsup.app.micro.order.presentation.dto.CreateOrderRequest;
import com.tecsup.app.micro.order.presentation.dto.OrderResponse;
import com.tecsup.app.micro.order.presentation.mapper.OrderDtoMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Orders (Órdenes)", description = "API para la gestión de ventas y registro de órdenes de compra.")
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderApplicationService orderApplicationService;
    private final OrderDtoMapper orderDtoMapper;

    @Operation(summary = "Crear una nueva orden", description = "Valida la existencia de los productos en product-service y registra una nueva orden. Aplica política 'Fail-Fast' si el producto no existe.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Orden creada y guardada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de la petición inválidos (ej. stock negativo o faltan datos)"),
            @ApiResponse(responseCode = "404", description = "Uno o más productos no encontrados en el catálogo externo")
    })
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        log.info("REST para crear orden: {}", request.getUserId());

        Order orderToCreate = orderDtoMapper.toDomain(request);
        Order createdOrder = orderApplicationService.createOrder(orderToCreate);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderDtoMapper.toResponse(createdOrder));
    }

    @Operation(summary = "Listar todas las órdenes", description = "Obtiene el historial completo de todas las órdenes en el sistema. Incluye comunicación con product-service y tolerancia a fallos.")
    @ApiResponse(responseCode = "200", description = "Lista de órdenes obtenida correctamente")
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        log.info("REST para obtener todas las ordenes");
        List<Order> orders = orderApplicationService.getAllOrders();
        return ResponseEntity.ok(orderDtoMapper.toResponseList(orders));
    }

    @Operation(summary = "Buscar orden por ID", description = "Devuelve el detalle exacto de una orden específica usando su identificador interno.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orden encontrada y detallada"),
            @ApiResponse(responseCode = "404", description = "La orden con el ID solicitado no existe en la base de datos")
    })
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(
            @Parameter(description = "ID único de la orden", example = "1") @PathVariable Long id) {
        log.info("REST para obtener orden por ID: {}", id);
        Order order = orderApplicationService.getOrderById(id);
        return ResponseEntity.ok(orderDtoMapper.toResponse(order));
    }

    @Operation(summary = "Buscar órdenes por Usuario", description = "Obtiene el historial completo de compras realizadas por un cliente específico.")
    @ApiResponse(responseCode = "200", description = "Historial del usuario obtenido correctamente")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponse>> getOrdersByUser(
            @Parameter(description = "ID del usuario comprador", example = "1") @PathVariable Long userId) {
        log.info("REST para obtener ordenes por usuario: {}", userId);
        List<Order> orders = orderApplicationService.getOrdersByUser(userId);
        return ResponseEntity.ok(orderDtoMapper.toResponseList(orders));
    }

    @Operation(summary = "Health Check del Servicio", description = "Endpoint de diagnóstico para verificar que el microservicio está levantado y respondiendo.")
    @ApiResponse(responseCode = "200", description = "Servicio saludable")
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Order Service running with Clean Architecture!");
    }
}