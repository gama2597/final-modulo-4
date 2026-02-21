package com.tecsup.app.micro.order.presentation.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {

    @NotNull(message = "User ID es obligatorio")
    private Long userId;

    @NotEmpty(message = "Orden debe contener al menos un item")
    @Valid
    private List<OrderItemRequest> items;
}