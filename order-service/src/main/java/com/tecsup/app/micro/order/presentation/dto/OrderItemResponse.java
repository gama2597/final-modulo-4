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

    private Long id;

    @JsonIgnore
    private Long productId;

    @JsonProperty("product")
    private Product productDetails;

    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;
}