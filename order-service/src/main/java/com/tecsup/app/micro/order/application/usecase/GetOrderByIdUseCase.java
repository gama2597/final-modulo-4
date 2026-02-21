package com.tecsup.app.micro.order.application.usecase;

import com.tecsup.app.micro.order.domain.model.Order;
import com.tecsup.app.micro.order.domain.model.Product;
import com.tecsup.app.micro.order.domain.port.OrderRepositoryPort;
import com.tecsup.app.micro.order.domain.port.ProductServicePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetOrderByIdUseCase {

    private final OrderRepositoryPort orderRepository;
    private final ProductServicePort productService;

    public Order execute(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada con ID: " + id));

        if (order.getItems() != null) {
            order.getItems().forEach(item -> {
                try {
                    var productDetails = productService.getProductById(item.getProductId());
                    item.setProductDetails(productDetails);
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

        return order;
    }
}