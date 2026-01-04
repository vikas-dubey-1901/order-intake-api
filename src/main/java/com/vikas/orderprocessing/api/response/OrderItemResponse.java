package com.vikas.orderprocessing.api.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class OrderItemResponse {

    private String productId;
    private int quantity;
    private BigDecimal unitPrice;
}
