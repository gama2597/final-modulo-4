package com.tecsup.app.micro.order.infrastructure.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Este DTO refleja exactamente el JSON que devuelve el GET /api/products/{id}
 * del microservicio product-service.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private Integer stock;
    private boolean available;
}