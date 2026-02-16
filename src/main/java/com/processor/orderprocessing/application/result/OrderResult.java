package com.processor.orderprocessing.application.result;

import com.processor.orderprocessing.domain.domainEnum.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class OrderResult {

    private UUID orderId;
    private OrderStatus status;
    private String message;

    public static OrderResult success(
            UUID orderId,
            OrderStatus status,
            String message
    ) {
        return new OrderResult(orderId, status, message);
    }
}
