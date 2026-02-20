package com.tecsup.app.micro.order.infrastructure.persistence.repository;

import com.tecsup.app.micro.order.infrastructure.persistence.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaOrderRepository extends JpaRepository<OrderEntity, Long> {
    
    // Spring Data JPA crea la query automáticamente con solo nombrar bien el método
    List<OrderEntity> findByUserId(Long userId);
    
}