package com.processor.orderprocessing.domain.model;

import com.processor.orderprocessing.domain.domainEnum.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public class OrderStatusHistory {

    private OrderStatus status;
    private Instant changedAt;
}
