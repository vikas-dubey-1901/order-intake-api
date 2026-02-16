package com.processor.orderprocessing.application.view;

import com.processor.orderprocessing.domain.model.Order;
import com.processor.orderprocessing.domain.model.OrderItem;
import com.processor.orderprocessing.domain.domainEnum.OrderChannel;
import com.processor.orderprocessing.domain.domainEnum.OrderStatus;
import com.processor.orderprocessing.domain.domainEnum.OrderType;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Builder
public class OrderView {

    private UUID orderId;
    private OrderType orderType;
    private OrderChannel channel;
    private String customerId;
    private OrderStatus status;
    private String currency;
    private List<OrderItemView> items;
    private Instant createdAt;
    private Instant updatedAt;

    public static OrderView from(Order order) {

        return OrderView.builder()
                .orderId(order.getId())
                .orderType(order.getOrderType())
                .channel(order.getChannel())
                .customerId(order.getCustomerId())
                .status(order.getStatus())
                .currency(order.getCurrency())
                .items(
                        order.getItems()
                                .stream()
                                .map(OrderItemView::from)
                                .toList()
                )
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }
}
