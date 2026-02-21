package com.tecsup.app.micro.order.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Objeto que representa un ítem individual dentro de la solicitud de compra")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequest {

    @Schema(description = "ID del producto existente en el catálogo (product-service)", example = "7")
    @NotNull(message = "Product ID es requerido")
    private Long productId;

    @Schema(description = "Cantidad de unidades que se desean comprar de este producto", example = "2")
    @NotNull(message = "Quantity es requerido")
    @Min(value = 1, message = "Quantity debe ser al menos 1")
    private Integer quantity;
}