package com.tecsup.app.micro.order.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private Long id;
    private String orderNumber; // UUID único para rastreo

    // --- NUEVOS CAMPOS AGREGADOS ---
    private Long userId; // ID del usuario que compró (viene de user-service)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    // -------------------------------

    private String status; // PENDING, COMPLETED, CANCELLED
    private BigDecimal totalAmount;

    @Builder.Default
    private List<OrderItem> items = new ArrayList<>();

    // --- MÉTODOS DE NEGOCIO ---

    public void calculateTotal() {
        this.totalAmount = items.stream()
                .map(OrderItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public boolean isValid() {
        return items != null && !items.isEmpty() && userId != null;
    }
}