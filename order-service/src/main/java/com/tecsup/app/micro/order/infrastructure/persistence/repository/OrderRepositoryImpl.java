package com.tecsup.app.micro.order.infrastructure.persistence.repository;

import com.tecsup.app.micro.order.domain.model.Order;
import com.tecsup.app.micro.order.domain.port.OrderRepositoryPort;
import com.tecsup.app.micro.order.infrastructure.persistence.entity.OrderEntity;
import com.tecsup.app.micro.order.infrastructure.persistence.mapper.OrderPersistenceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderRepositoryImpl implements OrderRepositoryPort {

    private final JpaOrderRepository jpaOrderRepository;
    private final OrderPersistenceMapper mapper;

    @Override
    public Order save(Order order) {
        log.debug("Guardando orden en la base de datos");
        OrderEntity entity = mapper.toEntity(order);
        OrderEntity savedEntity = jpaOrderRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Order> findById(Long id) {
        log.debug("Buscando orden por id: {}", id);
        return jpaOrderRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Order> findAll() {
        log.debug("Buscando todas las ordenes");
        return mapper.toDomainList(jpaOrderRepository.findAll());
    }

    @Override
    public List<Order> findByUserId(Long userId) {
        log.debug("Buscando ordenes por userId: {}", userId);
        return mapper.toDomainList(jpaOrderRepository.findByUserId(userId));
    }

    @Override
    public void deleteById(Long id) {
        log.debug("Eliminando orden por id: {}", id);
        jpaOrderRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        log.debug("Validando existencia de orden por id: {}", id);
        return jpaOrderRepository.existsById(id);
    }
}