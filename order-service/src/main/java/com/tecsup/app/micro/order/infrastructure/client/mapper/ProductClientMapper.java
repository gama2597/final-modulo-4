package com.tecsup.app.micro.order.infrastructure.client.mapper;

import com.tecsup.app.micro.order.domain.model.Product;
import com.tecsup.app.micro.order.infrastructure.client.dto.ProductResponseDto;
import org.mapstruct.Mapper;

/**
 * Mapper para convertir la respuesta del microservicio externo (DTO)
 * a nuestro modelo de dominio puro usando MapStruct.
 */
@Mapper(componentModel = "spring")
public interface ProductClientMapper {
    
    Product toDomain(ProductResponseDto dto);

}