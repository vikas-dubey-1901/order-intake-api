package com.processor.orderprocessing.api.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class OrderHistoryResponse {

    private UUID orderId;
    private List<OrderStatusEntryResponse> history;
}
