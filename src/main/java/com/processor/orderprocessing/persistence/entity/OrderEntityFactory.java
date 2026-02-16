package com.processor.orderprocessing.persistence.entity;

import com.processor.orderprocessing.domain.domainEnum.OrderChannel;
import com.processor.orderprocessing.domain.domainEnum.OrderStatus;
import com.processor.orderprocessing.domain.domainEnum.OrderType;

import java.time.Instant;
import java.util.UUID;

public final class OrderEntityFactory {

    private OrderEntityFactory() {
        // prevent instantiation
    }

    public static OrderEntity create(
            UUID id,
            OrderType orderType,
            OrderChannel channel,
            String customerId,
            OrderStatus status,
            String currency,
            Instant createdAt,
            Instant updatedAt
    ) {
        OrderEntity entity = new OrderEntity();
        entity.id = id;
        entity.orderType = orderType;
        entity.channel = channel;
        entity.customerId = customerId;
        entity.status = status;
        entity.currency = currency;
        entity.createdAt = createdAt;
        entity.updatedAt = updatedAt;

        return entity;
    }
}
