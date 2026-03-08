package com.processor.orderprocessing.messaging.event;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OrderProcessedEvent {

    private UUID orderId;
    private String status;
    private String correlationId;

}