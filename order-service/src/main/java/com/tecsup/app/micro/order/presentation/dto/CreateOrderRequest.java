package com.tecsup.app.micro.order.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "Objeto que representa la solicitud principal para crear una nueva orden de compra")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {

    @Schema(description = "ID único del usuario que está realizando la compra", example = "1")
    @NotNull(message = "User ID es obligatorio")
    private Long userId;

    @Schema(description = "Lista de productos que se van a incluir en la orden")
    @NotEmpty(message = "Orden debe contener al menos un item")
    @Valid
    private List<OrderItemRequest> items;
}