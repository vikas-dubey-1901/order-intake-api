package com.vikas.orderprocessing.api.response;

import com.vikas.orderprocessing.domain.domainEnum.OrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class OrderStatusEntry {
    private OrderStatus status;
    private Instant timestamp;
}
