package com.processor.orderprocessing.application.event;

import com.processor.orderprocessing.domain.model.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class OutboxEvent {

    private String aggregateType;
    private String aggregateId;
    private String eventType;
    private String payload;
}

