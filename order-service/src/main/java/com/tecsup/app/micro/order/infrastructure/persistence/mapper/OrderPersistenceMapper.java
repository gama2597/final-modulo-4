package com.tecsup.app.micro.order.infrastructure.persistence.mapper;

import com.tecsup.app.micro.order.domain.model.Order;
import com.tecsup.app.micro.order.domain.model.OrderItem;
import com.tecsup.app.micro.order.infrastructure.persistence.entity.OrderEntity;
import com.tecsup.app.micro.order.infrastructure.persistence.entity.OrderItemEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

// 🚨 AQUÍ ESTÁ LA BUENA PRÁCTICA 🚨
// Le decimos a MapStruct que apague el Builder para poder usar nuestro @AfterMapping
@Mapper(componentModel = "spring", builder = @org.mapstruct.Builder(disableBuilder = true))
public interface OrderPersistenceMapper {

    // --- De Entity a Dominio ---
    Order toDomain(OrderEntity entity);
    List<Order> toDomainList(List<OrderEntity> entities);

    @Mapping(target = "productDetails", ignore = true) // Este campo no existe en la BD
    OrderItem toDomainItem(OrderItemEntity entity);


    // --- De Dominio a Entity ---
    OrderEntity toEntity(Order order);

    @Mapping(target = "order", ignore = true) // Evita ciclos infinitos al mapear
    OrderItemEntity toEntityItem(OrderItem item);

    /**
     * TRUCO DE JPA: Cuando pasamos de Dominio a Entity, los hijos (Items) 
     * necesitan conocer quién es su padre (Order).
     * Este método se ejecuta automáticamente después de que MapStruct hace su magia.
     */
    @AfterMapping
    default void linkItemsToOrder(@MappingTarget OrderEntity orderEntity) {
        if (orderEntity.getItems() != null) {
            orderEntity.getItems().forEach(item -> item.setOrder(orderEntity));
        }
    }
}