package com.tecsup.app.micro.order.application.usecase;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.tecsup.app.micro.order.domain.exception.StockNotAvailableException;
import com.tecsup.app.micro.order.domain.model.Order;
import com.tecsup.app.micro.order.domain.model.OrderItem;
import com.tecsup.app.micro.order.domain.model.Product;
import com.tecsup.app.micro.order.domain.port.OrderRepositoryPort;
import com.tecsup.app.micro.order.domain.port.ProductServicePort;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreateOrderUseCase {

    private final OrderRepositoryPort orderRepository;
    private final ProductServicePort productServicePort;

    public Order execute(Order order) {
        log.info("Ejecutando CreateOrderUseCase para el usuario: {}", order.getUserId());

        int currentYear = LocalDateTime.now().getYear();

        // 1. Validar reglas de negocio básicas
        if (!order.isValid()) {
            throw new IllegalArgumentException("Orden debe contener al menos un ítem con cantidad mayor a cero");
        }

        // 2. Procesar cada ítem (validar stock y obtener precio real)
        for (OrderItem item : order.getItems()) {
            // Llamada al puerto externo (ProductService)
            Product productInfo = productServicePort.getProductById(item.getProductId());

            // Regla de Negocio: Validar Stock
            if (productInfo.getStock() < item.getQuantity()) {
                throw new StockNotAvailableException(
                        "Stock insuficiente para el producto: " + productInfo.getName());
            }

            // Regla de Negocio: Usar siempre el precio actual del sistema, NO el del
            // frontend
            item.setUnitPrice(productInfo.getPrice());

            // Calcular subtotal del ítem (método del dominio)
            item.calculateSubtotal();

            // Opcional: Guardar detalles del producto para responder al cliente (no se
            // guarda en BD)
            item.setProductDetails(productInfo);
        }

        // 3. Calcular total de la orden (método del dominio)
        order.calculateTotal();

        // 4. Completar datos de auditoría y estado
        // Genera un número aleatorio de 3 dígitos con ceros a la izquierda (ej. 007,
        // 042, 999)
        String randomNum = String.format("%03d", (int) (Math.random() * 1000));

        // Crea el formato: ORD-2026-042
        order.setOrderNumber("ORD-" + currentYear + "-" + randomNum);
        order.setStatus("PENDING");
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());

        // 5. Guardar en BD a través del puerto
        Order savedOrder = orderRepository.save(order);

        for (int i = 0; i < order.getItems().size(); i++) {
            savedOrder.getItems().get(i).setProductDetails(
                    order.getItems().get(i).getProductDetails());
        }

        log.info("Orden creada satisfactoriamente con ID: {}", savedOrder.getId());

        return savedOrder;
    }
}