package com.tecsup.app.micro.order.infrastructure.client.mapper;

import com.tecsup.app.micro.order.domain.model.Product;
import com.tecsup.app.micro.order.infrastructure.client.dto.ProductResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductClientMapper {
    
    Product toDomain(ProductResponseDto dto);

}