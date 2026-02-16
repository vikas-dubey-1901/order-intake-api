package com.processor.orderprocessing.domain.model;

import com.processor.orderprocessing.domain.domainEnum.OrderChannel;
import com.processor.orderprocessing.domain.domainEnum.OrderStatus;
import com.processor.orderprocessing.domain.domainEnum.OrderType;
import com.processor.orderprocessing.domain.exception.InvalidOrderStateException;
import com.processor.orderprocessing.domain.exception.OrderValidationException;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class Order {

    private final UUID id;
    private final OrderType orderType;
    private final OrderChannel channel;
    private final String customerId;
    private final List<OrderItem> items;
    private final String currency;

    private OrderStatus status;

    private  Instant createdAt;
    private Instant updatedAt;

    private Order(
            UUID id,
            OrderType orderType,
            OrderChannel channel,
            String customerId,
            List<OrderItem> items,
            String currency
    ) {
        this.id = id;
        this.orderType = orderType;
        this.channel = channel;
        this.customerId = customerId;
        this.items = List.copyOf(items);
        this.currency = currency;

        this.status = OrderStatus.RECEIVED;
        this.createdAt = Instant.now();
        this.updatedAt = this.createdAt;
    }

    public static Order rehydrate(
            UUID id,
            OrderType orderType,
            OrderChannel channel,
            String customerId,
            List<OrderItem> items,
            String currency,
            OrderStatus status,
            Instant createdAt,
            Instant updatedAt
    ) {
        Order order = new Order(
                id,
                orderType,
                channel,
                customerId,
                items,
                currency
        );
        order.status = status;
        order.createdAt = createdAt;
        order.updatedAt = updatedAt;
        return order;
    }

    public static Order create(OrderType orderType, OrderChannel channel, String customerId, List<OrderItem> items, String currency) {
        if (items == null || items.isEmpty()) {
            throw new OrderValidationException("Order must contain at least one item");
        }

        return new Order(UUID.randomUUID(), orderType, channel, customerId, items, currency);
    }

    public void markValidated() {
        if (status != OrderStatus.RECEIVED) {
            throw new InvalidOrderStateException(
                    "Only RECEIVED orders can be validated"
            );
        }
        this.status = OrderStatus.VALIDATED;
        touch();
    }

    public void markRejected() {
        if (status != OrderStatus.RECEIVED) {
            throw new InvalidOrderStateException(
                    "Only RECEIVED orders can be rejected"
            );
        }
        this.status = OrderStatus.REJECTED;
        touch();
    }

    public void cancel() {
        if (status != OrderStatus.RECEIVED && status != OrderStatus.VALIDATED) {
            throw new InvalidOrderStateException(
                    "Order cannot be cancelled in state " + status
            );
        }
        this.status = OrderStatus.CANCELLED;
        touch();
    }

    private void touch() {
        this.updatedAt = Instant.now();
    }

    public BigDecimal totalAmount() {
        return items.stream()
                .map(OrderItem::lineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


}
