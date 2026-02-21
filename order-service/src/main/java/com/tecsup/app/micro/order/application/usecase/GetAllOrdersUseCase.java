package com.tecsup.app.micro.order.application.usecase;

import com.tecsup.app.micro.order.domain.model.Order;
import com.tecsup.app.micro.order.domain.model.Product;
import com.tecsup.app.micro.order.domain.port.OrderRepositoryPort;
import com.tecsup.app.micro.order.domain.port.ProductServicePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetAllOrdersUseCase {

    private final OrderRepositoryPort orderRepository;
    private final ProductServicePort productServicePort;

    public List<Order> execute() {
        List<Order> orders = orderRepository.findAll();

        orders.forEach(order -> {
            if (order.getItems() != null) {
                order.getItems().forEach(item -> {
                    try {
                        var productInfo = productServicePort.getProductById(item.getProductId());
                        item.setProductDetails(productInfo);
                    } catch (Exception e) {
                        log.warn("Producto con ID {} no encontrado para la orden {}. Asignando valores por defecto.",
                                item.getProductId(), order.getId());

                        Product fallbackProduct = Product.builder()
                                .id(item.getProductId())
                                .name("Producto no disponible")
                                .price(item.getUnitPrice())
                                .build();
                        item.setProductDetails(fallbackProduct);
                    }
                });
            }
        });

        return orders;
    }
}