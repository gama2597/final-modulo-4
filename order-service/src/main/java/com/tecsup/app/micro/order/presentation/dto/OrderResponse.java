package com.tecsup.app.micro.order.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Objeto que representa la respuesta completa de una orden de compra procesada")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    @Schema(description = "ID único de la orden en la base de datos", example = "1")
    private Long id;

    @Schema(description = "Código de seguimiento único generado para la orden", example = "ORD-20260220-A1B2")
    private String orderNumber;

    @Schema(description = "ID del usuario que realizó la compra", example = "1")
    private Long userId;

    @Schema(description = "Estado actual de la orden", example = "COMPLETED")
    private String status;

    @Schema(description = "Monto total a pagar por toda la orden", example = "901.98")
    private BigDecimal totalAmount;

    @Schema(description = "Fecha y hora exacta en la que se registró la compra", example = "2026-02-20T10:40:12")
    private LocalDateTime createdAt;

    @Schema(description = "Lista detallada de los ítems comprados en esta orden")
    private List<OrderItemResponse> items;
}