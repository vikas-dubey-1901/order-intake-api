package com.processor.orderprocessing.api.response;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class OrderStatusEntryResponse {

    private String status;
    private Instant timestamp;
}
