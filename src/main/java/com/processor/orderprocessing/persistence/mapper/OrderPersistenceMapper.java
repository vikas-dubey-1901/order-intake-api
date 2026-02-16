package com.processor.orderprocessing.persistence.mapper;

import com.processor.orderprocessing.domain.model.Order;
import com.processor.orderprocessing.persistence.entity.OrderEntity;
import com.processor.orderprocessing.persistence.entity.OrderItemEntity;
import org.springframework.stereotype.Component;

@Component
public class OrderPersistenceMapper {

    public OrderEntity toEntity(Order domain) {

        OrderEntity entity = OrderEntity.fromDomain(
                domain.getId(),
                domain.getOrderType(),
                domain.getChannel(),
                domain.getCustomerId(),
                domain.getStatus(),
                domain.getCurrency(),
                domain.getCreatedAt(),
                domain.getUpdatedAt()
        );

        domain.getItems().forEach(item -> {
            OrderItemEntity itemEntity =
                    OrderItemEntity.fromDomain(
                            item.getProductId(),
                            item.getQuantity(),
                            item.getUnitPrice()
                    );
            entity.addItem(itemEntity);
        });

        return entity;
    }
}
