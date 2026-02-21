package com.tecsup.app.micro.order.infrastructure.client;

import com.tecsup.app.micro.order.domain.exception.ExternalServiceException;
import com.tecsup.app.micro.order.domain.exception.ProductNotFoundException;
import com.tecsup.app.micro.order.domain.model.Product;
import com.tecsup.app.micro.order.domain.port.ProductServicePort;
import com.tecsup.app.micro.order.infrastructure.client.dto.ProductResponseDto;
import com.tecsup.app.micro.order.infrastructure.client.mapper.ProductClientMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductClient implements ProductServicePort {

    private final RestTemplate restTemplate;
    private final ProductClientMapper productClientMapper;

    @Value("${app.client.product-service.url}")
    private String productServiceUrl;

    @Override
    public Product getProductById(Long productId) {
        log.info("Llamando a Product Service para obtener producto con id: {}", productId);

        String url = this.productServiceUrl + "/api/products/" + productId;

        try {
            ProductResponseDto response = restTemplate.getForObject(url, ProductResponseDto.class);
            log.info("Producto recuperado exitosamente de product-service: {}", response);
            
            return productClientMapper.toDomain(response);
            
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                log.warn("Producto con id {} no encontrado en product-service", productId);
                throw new ProductNotFoundException(productId);
            }
            log.error("Error HTTP al llamar a Product Service: {}", e.getMessage());
            throw new ExternalServiceException("Error al comunicarse con el servicio de productos: " + e.getMessage());
        } catch (Exception e) {
            log.error("Error inesperado al llamar a Product Service: {}", e.getMessage());
            throw new ExternalServiceException("Servicio de productos no disponible en este momento.");
        }
    }
}