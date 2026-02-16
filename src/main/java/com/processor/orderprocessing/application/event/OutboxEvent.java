package com.processor.orderprocessing.application.event;

import com.processor.orderprocessing.domain.model.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OutboxEvent {

    private String aggregateType;
    private String aggregateId;
    private String eventType;
    private Object payload;

    public static OutboxEvent orderReceived(Order order) {
        return new OutboxEvent(
                "ORDER",
                order.getId().toString(),
                "ORDER_RECEIVED",
                order
        );
    }

    public static OutboxEvent orderCancelled(Order order) {
        return new OutboxEvent(
                "ORDER",
                order.getId().toString(),
                "ORDER_CANCELLED",
                order
        );
    }
}

