package com.vikas.orderprocessing.api.response;

import com.vikas.orderprocessing.domain.domainEnum.OrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Builder
public class OrderSummaryResponse {
    private UUID orderId;
    private OrderStatus status;
    private String customerId;
    private Instant createdAt;
}
