package com.tecsup.app.micro.order.presentation.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequest {

    @NotNull(message = "Product ID es requerido")
    private Long productId;

    @NotNull(message = "Quantity es requerido")
    @Min(value = 1, message = "Quantity debe ser al menos 1")
    private Integer quantity;
}