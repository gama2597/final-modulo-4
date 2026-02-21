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

@Mapper(componentModel = "spring", builder = @org.mapstruct.Builder(disableBuilder = true))
public interface OrderPersistenceMapper {

    Order toDomain(OrderEntity entity);
    List<Order> toDomainList(List<OrderEntity> entities);

    @Mapping(target = "productDetails", ignore = true)
    OrderItem toDomainItem(OrderItemEntity entity);

    OrderEntity toEntity(Order order);

    @Mapping(target = "order", ignore = true)
    OrderItemEntity toEntityItem(OrderItem item);

    @AfterMapping
    default void linkItemsToOrder(@MappingTarget OrderEntity orderEntity) {
        if (orderEntity.getItems() != null) {
            orderEntity.getItems().forEach(item -> item.setOrder(orderEntity));
        }
    }
}