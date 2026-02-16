package com.processor.orderprocessing.application.view;

import com.processor.orderprocessing.domain.domainEnum.OrderStatus;
import com.processor.orderprocessing.domain.model.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class OrderSummaryView {

    private UUID orderId;
    private OrderStatus status;
    private String customerId;
    private Instant createdAt;

    public static OrderSummaryView from(Order order) {
        return new OrderSummaryView(
                order.getId(),
                order.getStatus(),
                order.getCustomerId(),
                order.getCreatedAt()
        );
    }
}
