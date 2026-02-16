package com.processor.orderprocessing.messaging.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemPayload {

    private String productId;
    private int quantity;
    private BigDecimal unitPrice;
}

