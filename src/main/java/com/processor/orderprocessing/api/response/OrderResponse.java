package com.processor.orderprocessing.api.response;

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
public class OrderResponse {
    private UUID orderId;
    private OrderType orderType;
    private OrderChannel channel;
    private String customerId;
    private OrderStatus status;
    private String currency;
    private List<OrderItemResponse> items;
    private Map<String, String> metadata;
    private Instant createdAt;
    private Instant updatedAt;
}
