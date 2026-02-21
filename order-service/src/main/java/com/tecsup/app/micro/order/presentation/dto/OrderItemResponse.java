package com.tecsup.app.micro.order.presentation.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tecsup.app.micro.order.domain.model.Product; // O la ruta correcta a tu clase Product
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Schema(description = "Objeto que representa el detalle de un ítem ya guardado en una orden")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponse {

    @Schema(description = "ID interno del ítem en la base de datos de órdenes", example = "150")
    private Long id;

    @JsonIgnore
    private Long productId;

    @Schema(description = "Detalles completos del producto (Nombre, precio, etc.) traídos desde product-service o fallback")
    @JsonProperty("product")
    private Product productDetails;

    @Schema(description = "Cantidad comprada", example = "2")
    private Integer quantity;

    @Schema(description = "Precio unitario del producto al momento de la compra (Histórico)", example = "450.99")
    private BigDecimal unitPrice;

    @Schema(description = "Costo total de este ítem (quantity * unitPrice)", example = "901.98")
    private BigDecimal subtotal;
}