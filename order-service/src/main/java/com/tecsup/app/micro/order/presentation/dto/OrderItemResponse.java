package com.tecsup.app.micro.order.presentation.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tecsup.app.micro.order.domain.model.Product; // O la ruta correcta a tu clase Product
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponse {

    // 1. Agregamos el ID del ítem (la llave primaria de order_items)
    private Long id;

    // 2. Ocultamos el productId suelto para que no ensucie el JSON
    @JsonIgnore
    private Long productId;

    // 3. ¡LA MAGIA! Renombramos "productDetails" a "product" en el JSON
    @JsonProperty("product")
    private Product productDetails;

    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;
}