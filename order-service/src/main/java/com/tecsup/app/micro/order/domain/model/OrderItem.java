package com.tecsup.app.micro.order.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    private Long id;

    private Long productId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;

    // Es para mostrar detalles al cliente
    private Product productDetails;

    /**
     * Calcula el subtotal basado en precio y cantidad
     */
    public void calculateSubtotal() {
        if (this.unitPrice != null && this.quantity != null) {
            this.subtotal = this.unitPrice.multiply(BigDecimal.valueOf(this.quantity));
        }
    }
}