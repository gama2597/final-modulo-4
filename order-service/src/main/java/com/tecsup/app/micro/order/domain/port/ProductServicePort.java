package com.tecsup.app.micro.order.domain.port;

import com.tecsup.app.micro.order.domain.model.Product;

public interface ProductServicePort {
    
    Product getProductById(Long id);

}