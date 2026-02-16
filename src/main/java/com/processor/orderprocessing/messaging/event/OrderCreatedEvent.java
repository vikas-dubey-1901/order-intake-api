package com.processor.orderprocessing.messaging.event;

import com.processor.orderprocessing.domain.domainEnum.OrderChannel;
import com.processor.orderprocessing.domain.domainEnum.OrderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderCreatedEvent {
    private String eventId;          // idempotency
    private String correlationId;

    private OrderType orderType;
    private OrderChannel channel;
    private String customerId;

    private List<OrderItemPayload> items;
    private String currency;

}
